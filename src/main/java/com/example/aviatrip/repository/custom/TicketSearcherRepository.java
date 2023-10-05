package com.example.aviatrip.repository.custom;

import com.example.aviatrip.enumeration.FlightSeatClass;
import com.example.aviatrip.model.entity.Airplane;
import com.example.aviatrip.model.entity.AviaCompany;
import com.example.aviatrip.model.entity.Flight;
import com.example.aviatrip.model.entity.FlightSeat;
import com.example.aviatrip.model.request.TicketFilterModel;
import com.example.aviatrip.model.response.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketSearcherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Ticket> findAll(TicketFilterModel model, Pageable pageable, boolean orderByPriceAsc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = cb.createQuery(Ticket.class);

        Root<FlightSeat> seat = query.from(FlightSeat.class);
        Join<Flight, FlightSeat> flight = seat.join("flight", JoinType.INNER);
        Join<AviaCompany, Flight> company = flight.join("company", JoinType.INNER);
        Join<Airplane, Flight> airplane = flight.join("airplane", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(flight.get("source"), model.getSource()));
        predicates.add(cb.equal(flight.get("destination"), model.getDestination()));
        predicates.add(getDistinctFlightSeatsByFlightIdAndSeatClassPredicate(cb, query, seat, model.getSeatClass()));
        predicates.add(cb.isNull(seat.get("customer")));
        predicates.add(getfilterTakeoffDateByDatePredicate(cb, flight, model.getTakeoffDate()));

        if(model.getCompanies().isPresent())
            predicates.add(company.get("name").in(model.getCompanies().get()));

        query.select(cb.construct(Ticket.class, getSelectionsForTicketClass(seat, flight, company, airplane)))
                .where(predicates.toArray(new Predicate[]{}));

        if(orderByPriceAsc)
            query.orderBy(cb.asc(seat.get("price")));

        return entityManager.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults((pageable.getPageSize()))
                .getResultList();
    }

    private Predicate getfilterTakeoffDateByDatePredicate(CriteriaBuilder cb, Join<Flight, FlightSeat> flight, LocalDate date) {
        ZonedDateTime currentTimestamp = ZonedDateTime.now(ZoneId.of("UTC"));

        ZonedDateTime dateAfter = ZonedDateTime.of(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), 0, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime dateBefore = dateAfter.plusDays(1);

        if(currentTimestamp.getYear() == date.getYear() && currentTimestamp.getDayOfYear() == date.getDayOfYear())
            dateAfter = currentTimestamp;

        return cb.between(flight.get("takeoffTimestamp"), dateAfter, dateBefore);
    }

    private Predicate getDistinctFlightSeatsByFlightIdAndSeatClassPredicate(CriteriaBuilder cb, CriteriaQuery<Ticket> query,
                                                                            Root<FlightSeat> seat, FlightSeatClass seatClass) {
        Subquery<Number> sub = query.subquery(Number.class);
        Root<FlightSeat> subRoot = sub.from(FlightSeat.class);

        sub.select(cb.min(subRoot.get("id")))
                .where(cb.equal(subRoot.get("seatClass"), seatClass))
                .groupBy(subRoot.get("flight"));

        return seat.get("id").in(List.of(sub));
    }

    private Selection<?>[] getSelectionsForTicketClass(Root<FlightSeat> seat, Join<Flight, FlightSeat> flight, Join<AviaCompany, Flight> company, Join<Airplane, Flight> airplane) {
        return new Selection[]{
                seat.get("id"),
                seat.get("isWindowSeat"),
                seat.get("position"),
                seat.get("price"),
                flight.get("id"),
                flight.get("destination"),
                flight.get("landingTimestamp"),
                flight.get("source"),
                flight.get("takeoffTimestamp"),
                company.get("name"),
                airplane.get("model")
        };
    }
}
