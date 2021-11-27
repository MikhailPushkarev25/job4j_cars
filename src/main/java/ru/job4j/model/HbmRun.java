package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HbmRun {
    public static void main(String[] args) {
     final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
             .configure().build();

     try {
         SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
         Session session = sf.openSession();
         session.beginTransaction();

         Car car = Car.of("Toyota");
         session.save(car);

         Body body = Body.of("hatchback");
         session.save(body);

         User user = User.of("Mikhail", "mikhail@mail.ru", "123");
         session.save(user);

         Info info = Info.of("Информация об автомобиле владельца", true, car, body);
         session.save(info);

         session.getTransaction().commit();
         session.close();
     } catch (Exception e) {
         e.printStackTrace();
     } finally {
         StandardServiceRegistryBuilder.destroy(registry);
     }
    }
}
