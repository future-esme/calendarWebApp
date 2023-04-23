package utm.tmps.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utm.tmps.domain.Event;
import utm.tmps.domain.User;

/**
 * Spring Data JPA repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query(value = """
        select cast(date_part('day', start_time + interval '3 hours') as int) from event
        where (date_part('month', start_time + interval '3 hours') = :month and date_part('year', start_time) = :year)
           union
        select cast(date_part('day', end_time + interval '3 hours') as int) from event
        where (date_part('month', end_time + interval '3 hours') = :month and date_part('year', end_time) = :year);
    """, nativeQuery = true)
    List<Integer> getDaysWithEvents(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value = """
        select * from event
        where user_id = :userId and (
        (start_time >= :startTime and start_time <= :endTime)
        or (end_time <= :endTime and end_time >= :startTime));
    """, nativeQuery = true)
    List<Event> findUserEventByDay(@Param("userId") UUID userStart, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    @Query(value = """
        select * from event
        where notification_time >= cast(:startTime as timestamp)
        and notification_time <= cast(:endTime as timestamp)
        and (event.send_email_notification or event.send_push_notification)
    """, nativeQuery = true)
    List<Event> findEventsNeedToBeNotified(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
}
