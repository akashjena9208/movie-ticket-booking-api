# Understanding the N+1 Problem in Your JPA Code and How to Solve It

## What is the N+1 Problem?

The **N+1 problem** is a common performance issue in applications using ORM frameworks like JPA and Hibernate. It occurs when:

- The application makes **1 query** to fetch a parent entity (e.g., `Screen`),
- Then makes **N additional queries** to fetch each associated child entity (e.g., `Seat`) individually.

This results in excessive database queries (N+1 queries), causing slow performance and high database load.

---

## How the N+1 Problem Appears in Your Code

- When saving seats in your current code, you call `seatRepository.save(seat)` **inside a loop**, causing one database call per seat created.
- Even though you save your `Screen`, each seat is saved separately, leading to many DB calls rather than one batch operation.
- This is a classic example of the N+1 saves problem on **write operations**.

---

## Why is N+1 a Problem?

- It leads to **performance degradation** due to numerous database calls.
- It increases network overhead and DB contention.
- Slows down the application, especially when handling large volumes of seats.

---

## How to Solve the N+1 Problem in Your Code

### 1. Use Cascade Persist to Save All Seats in One Transaction

- Configure your `Screen` entity’s seat list with  
  `@OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)`
- Create **all seats in memory** and add them to a list.
- Attach the full seat list to the screen entity using `screen.setSeats(seats)`.
- Call `screenRepository.save(screen)` once — JPA cascades the save to all seats.

This reduces multiple insert calls to a single batch insert operation.

### 2. Avoid Saving Seats Individually in a Loop

- Remove `seatRepository.save(seat)` from inside the seat creation loop.
- Instead, save all seats automatically by saving the parent screen entity after attaching all seats.

---

## Example Refactored Code Snippet

private List<Seat> createSeats(Screen screen, Integer capacity) {
List<Seat> seats = new ArrayList<>();
int noOfSeatsPerRow = screen.getCapacity() / screen.getNoOfRows();
char row = 'A';

for (int i = 1, j = 1; i <= capacity; i++, j++) {
Seat seat = new Seat();
seat.setScreen(screen);
seat.setDelete(false);
seat.setName(row + "" + j);
seats.add(seat);

    if (j == noOfSeatsPerRow) {
        j = 0;
        row++;
    }
}

screen.setSeats(seats);
screenRepository.save(screen);  // One DB call cascades to all seats
return seats;

---

## Summary

| Aspect              | Current Implementation             | Recommended Fix                   |
|---------------------|----------------------------------|---------------------------------|
| Seat Saving Method  | Save each seat individually (N calls) | Save screen once with cascade (1 call) |
| Database Calls      | Many, slow                       | Single batch, fast               |
| Performance Impact  | High latency and overhead       | Significantly improved           |

---

## Benefits of Fixing N+1

- Drastically reduces the number of database operations.
- Ensures atomic saving of screen and seats.
- Improves overall application performance and scalability.

---

## References

- [Baeldung: N+1 Problem in Hibernate and Spring Data JPA](https://www.baeldung.com/spring-hibernate-n1-problem)
- [Vlad Mihalcea: N+1 Query Problem](https://vladmihalcea.com/n-plus-1-query-problem/)
- [StackOverflow: What is the N+1 Selects Problem](https://stackoverflow.com/questions/97197/what-is-the-n1-selects-problem-in-orm-object-relational-mapping)  
