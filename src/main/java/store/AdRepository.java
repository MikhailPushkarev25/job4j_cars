package store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Info;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class AdRepository {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

    }

    public List<Info> extraction() {
        LocalDate date = LocalDate.now().minusDays(1);
        return this.tx(
                session -> session.createQuery("select i from Info i where i.created > :date", Info.class)
                    .setParameter("date", date).getResultList());
    }

    public List<Info> Photo() {
        return this.tx(
                session -> session.createQuery("select i from Info i where i.photo = true", Info.class)
                .getResultList());
    }

    public List<Info> Mark(String mark) {
        return this.tx(
                session -> session.createQuery("select i from Info i where i.car.mark = :mark", Info.class)
                        .setParameter("mark", mark).getResultList());
    }

}
