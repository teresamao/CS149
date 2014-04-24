//============================================================================
// Name        : ticket.c
// Author      : minglu ma
// Version     :
// Copyright   : Your copyright notice
// Description : 
//============================================================================

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <signal.h>
#include <semaphore.h>
#include <sys/time.h>
#include <string.h>

#define SELLER_NAME_LEN 2
#define ROW_NUM 10
#define SEAT_NUM 10
#define TICKET_NUM (ROW_NUM * SEAT_NUM)

#define SELLER_NUM 10
#define MAX_QUEUE_LEN 50

// 60 minutes
#define EVENT_DURATION 60

#define INVALID_NUM -1

struct ticket_t {
  int row;
  int seat;
  int seller_id;
  int customer_id;
};

struct customer_info_t {
  int seller_id;
  int customer_id;
};


// Store all tickets in a one dimension array.
struct ticket_t tickets[TICKET_NUM];
// Next available seat number for each row, seat number starts from 0.
int next_seat_in_rows[ROW_NUM];
// next row for high/medium/low sellers
int next_high_row, next_medium_row, next_low_row;
// mutex protects tickets and available index.
pthread_mutex_t ticket_mutex;

// the length of expected queue.
int queue_length = 0;
// queues stores customer id.
int queues[SELLER_NUM][MAX_QUEUE_LEN];
// Indexes of the in/out position of the each queue.
int queues_in[SELLER_NUM], queues_out[SELLER_NUM];
// mutex protects queues and in/out indexes.
pthread_mutex_t queues_mutex[SELLER_NUM];
// semaphore for notifying sellers there's a customer.
sem_t filled_queue[SELLER_NUM];
// The handled customer number for each seller.
int handled_customer_count[SELLER_NUM];

// sellers ids.
int seller_ids[SELLER_NUM];
// information for customers in each seller queue
struct customer_info_t customer_infos[MAX_QUEUE_LEN * SELLER_NUM];

// mutex protects printing
pthread_mutex_t print_mutex;

// timer to control 1 hour events
struct itimerval timer;
// Ticket selling start time.
time_t start_time;
// Flag indicates whether time is up.
int times_up = 0;

int sold_out = 0;


void print(char *event) {
   time_t now;
   time(&now);
   double elapsed = difftime(now, start_time);
   int min = 0;
   int sec = (int) elapsed;

   if (sec >= 60) {
     min++;
     sec -= 60;
   }

   pthread_mutex_lock(&print_mutex);
   // Elapsed time.
   printf("%1d:%02d | ", min, sec);
   printf(" %s\n", event);
   fflush(stdout);
   pthread_mutex_unlock(&print_mutex);
}

void initialize_tickets() {
  int row, seat;
  struct ticket_t* ticket;
  for (row = 0; row < ROW_NUM; row++) {
    for (seat = 0; seat < SEAT_NUM; seat++) {
      ticket = &tickets[row * SEAT_NUM + seat];
      ticket->row = row;
      ticket->seat = seat;
      ticket->seller_id = INVALID_NUM;
      ticket->customer_id = INVALID_NUM;
    }

    next_seat_in_rows[row] = 0;
  }
  next_high_row = 0;
  next_medium_row = 5;
  next_low_row = 9;
}

void initialize_queues() {
  int i, j;
  for (i = 0; i < SELLER_NUM; i++) {
    queues_in[i] = 0;
    queues_out[i] = 0;
    for (j = 0; j < MAX_QUEUE_LEN; j++) {
      queues[i][j] = INVALID_NUM;
    }
    handled_customer_count[i] = 0;
  }
}

int is_high_seller(int seller_id) {
  return seller_id == 0;
}

int is_med_seller(int seller_id) {
  return seller_id >= 1 && seller_id <= 3;
}

int is_low_seller(int seller_id) {
  return seller_id >= 4 && seller_id <= 9;
}

int has_ticket_in_row(int row) {
  return next_seat_in_rows[row] < SEAT_NUM;
}

int get_next_high_row() {
  return next_high_row < ROW_NUM - 1 ? next_high_row + 1 : INVALID_NUM;
}

struct ticket_t* get_next_ticket_for_high() {
  if (next_high_row == INVALID_NUM) {
    return NULL;
  }
  while (!has_ticket_in_row(next_high_row)) {
    next_high_row = get_next_high_row();
  }

  if (next_high_row == INVALID_NUM) {
    return NULL;
  }

  struct ticket_t* ticket = &tickets[next_high_row * SEAT_NUM + next_seat_in_rows[next_high_row]];
  next_seat_in_rows[next_high_row]++;
  return ticket;
}

// 0123456789
// 4536271809
int get_next_medium_row() {
  return next_medium_row == 9
    ? INVALID_NUM
    : (next_medium_row < 5 ? 9-next_medium_row : 8-next_medium_row);
}

struct ticket_t* get_next_ticket_for_medium() {
  if (next_medium_row == INVALID_NUM) {
    return NULL;
  }
  while (!has_ticket_in_row(next_medium_row)) {
    next_medium_row = get_next_medium_row();
  }

  if (next_medium_row == INVALID_NUM) {
    return NULL;
  }

  struct ticket_t* ticket = &tickets[next_medium_row * SEAT_NUM + next_seat_in_rows[next_medium_row]];
  next_seat_in_rows[next_medium_row]++;
  return ticket;
}

int get_next_low_row() {
  return next_low_row > 0 ? next_low_row - 1 : INVALID_NUM;
}

struct ticket_t* get_next_ticket_for_low() {
  if (next_low_row == INVALID_NUM) {
    return NULL;
  }
  while (!has_ticket_in_row(next_low_row)) {
    next_low_row = get_next_low_row();
  }

  if (next_low_row == INVALID_NUM) {
    return NULL;
  }

  struct ticket_t* ticket = &tickets[next_low_row * SEAT_NUM + next_seat_in_rows[next_low_row]];
  next_seat_in_rows[next_low_row]++;
  return ticket;
}

struct ticket_t* get_next_ticket(int seller_id) {
  if (is_high_seller(seller_id)) {
    return get_next_ticket_for_high();
  } else if (is_med_seller(seller_id)) {
    return get_next_ticket_for_medium();
  } else {
    return get_next_ticket_for_low();
  }
}

int get_handle_time(int seller_id) {
  if (seller_id == 0) {
    // high
    return rand() % 2 + 1;
  } else if (seller_id < 4) {
    // medium
    return rand() % 3 + 2;
  } else {
    // low
    return rand() % 4 + 4;
  }
}

int get_time() {
  time_t now;
  time(&now);
  return (int) difftime(now, start_time);
}

void sellTicketToOneCustomer(int seller_id) {
  int customer_id;
  char event[80];
  if (!times_up && !sold_out && handled_customer_count[seller_id] < queue_length) {
    // seller wait for filled_queue semaphore for a customer.
    int ret = sem_wait(&filled_queue[seller_id]);
    if (ret < 0) {
      return;
    }
     // Acquire the mutex lock to protect the queue
    pthread_mutex_lock(&queues_mutex[seller_id]);
    // Removes a customer from the queue.
    customer_id = queues[seller_id][queues_out[seller_id]];
    queues_out[seller_id]++;
    pthread_mutex_unlock(&queues_mutex[seller_id]);

    handled_customer_count[seller_id]++;

    sprintf(event, "seller %d start handling customer %d",
        seller_id, customer_id);
    print(event);

    // Acquires ticket mutex to protect the tickets
    pthread_mutex_lock(&ticket_mutex);
    struct ticket_t *ticket = get_next_ticket(seller_id);
    pthread_mutex_unlock(&ticket_mutex);

    if (ticket) {
      ticket->customer_id = customer_id;
      ticket->seller_id = seller_id;
      sprintf(event, "seller %d got ticket (%d, %d) for customer %d",
          seller_id, ticket->row, ticket->seat, customer_id);
      print(event);
    } else {
      sprintf(event, "seller %d found tickets sold out, customer %d leaves",
          seller_id, customer_id);
      print(event);
      sold_out = 1;
      return;
    }

    // handle customer
    int sleep_time = get_handle_time(seller_id);
    sleep(sleep_time);

    sprintf(event, "seller %d finishes with customer %d", seller_id, customer_id);
    print(event);
  }
}

void *seller(void *param) {
  int seller_id = *((int *) param);

  // sell ticket for one customer until event hour is over.
  do {
    sellTicketToOneCustomer(seller_id);
  } while (!times_up && !sold_out && handled_customer_count[seller_id] < queue_length);

  return NULL;
}

void customerArrives(struct customer_info_t *info) {
  char event[80];
  int in;

  // Acquires queue mutex and update in.
  pthread_mutex_lock(&queues_mutex[info->seller_id]);

  // occupy current slot in the queue
  in = queues_in[info->seller_id];
  queues[info->seller_id][in] = info->customer_id;
  // move empty slot to next
  queues_in[info->seller_id]++;

  pthread_mutex_unlock(&queues_mutex[info->seller_id]);

  sprintf(event, "customer %d arrives to seller %d and waits",
      info->customer_id, info->seller_id);
  print(event);

  // Signal the "filledSlots" semaphore.
  sem_post(&filled_queue[info->seller_id]);
}

void *customer(void *param) {
  struct customer_info_t *info = (struct customer_info_t *) param;
  // customer comes and walks to queue at any time within 60 minutes.
  sleep(rand() % EVENT_DURATION);
  customerArrives(info);
  return NULL;
}

// Timer signal handler.
void timerHandler(int signal)
{
   times_up = 1;  // event hour is over
}

void print_seat_chart() {
  int i, j, k;
  int seller;
  char event[200];
  for (i = 0; i < ROW_NUM; i++) {
    sprintf(event, "%02d:", i);
    for (j = 0; j < SEAT_NUM; j++) {
      k = i * SEAT_NUM + j;
      seller = tickets[k].seller_id;
      if (seller == INVALID_NUM) {
        sprintf(event + strlen(event), " ----");
      } else {
        sprintf(event + strlen(event), " %c%d%02d",
         is_high_seller(seller) ? 'H' : (is_med_seller(seller) ? 'M' : 'L'),
         is_high_seller(seller) ? 0 : (is_med_seller(seller) ? seller : seller - 3),
         tickets[k].customer_id);
      }
    }
    print(event);
  }
}

int main(int argc, char* argv[]) {
  int i, j, k;

  queue_length = atoi(argv[1]);
  printf("queue len: %d\n", queue_length);

  // initialize tickets
  initialize_tickets();
  initialize_queues();

  // initialize mutexes and semaphors.
  pthread_mutex_init(&ticket_mutex, NULL);
  pthread_mutex_init(&print_mutex, NULL);
  for (i = 0; i < SELLER_NUM; i++) {
    sem_init(&filled_queue[i], 0, 0);
  }

  srand(time(0));

  // Set up timer.
  time(&start_time);
  timer.it_value.tv_sec = EVENT_DURATION;
  setitimer(ITIMER_REAL, &timer, NULL);

  // Set the timer signal handler.
  signal(SIGALRM, timerHandler);

  pthread_t seller_thread_ids[SELLER_NUM];
    // Seller threads
  for (i = 0; i < SELLER_NUM; i++) {
    seller_ids[i] = i;
    pthread_attr_t seller_attr;
    pthread_attr_init(&seller_attr);
    pthread_create(&seller_thread_ids[i], &seller_attr, seller, &seller_ids[i]);

    // Customer threads
    for (j = 0; j < queue_length; j++) {
      k = i * SELLER_NUM + j;
      customer_infos[k].seller_id = i;
      customer_infos[k].customer_id = j;
      pthread_t customer_thread_id;
      pthread_attr_t customer_attr;
      pthread_attr_init(&customer_attr);
      pthread_create(&customer_thread_id, &customer_attr, customer, &customer_infos[k]);
    }
  }

  // wait for all sellers to complete.
  for (i = 0; i < SELLER_NUM; i++) {
    pthread_join(seller_thread_ids[i], NULL);
  }
  
  sleep(2);
  print_seat_chart();

  for (i = 0; i < SELLER_NUM; i++) {
    sem_destroy(&filled_queue[i]);
  }

  pthread_exit(NULL);
  return 0;
}

