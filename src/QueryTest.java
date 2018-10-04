/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.SQLQuery;

import javax.swing.*;


/**
 *
 * @author mkonda
 */
public class QueryTest {

    private SessionFactory factory = null;

    private void init() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(TravelReview.class);
        factory = config.buildSessionFactory();

    }

    private void getAllTravelReviews() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();

        Query query = session.createQuery("from TravelReview");
        List<TravelReview> reviews = query.list();

        for (int i=0;i<reviews.size();i++) {
            System.out.println(reviews.get(i));
        }

        tx.commit();
    }

    private void getTravelReviewUniqueRecord() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        Query query = session.createQuery("from TravelReview where title='Limerick'");
        TravelReview review = (TravelReview) query.uniqueResult();
        System.out.println("Results:" + review);

        tx.commit();

    }

    private void getTravelReviewWithQueryParam(String city) {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        Query query = session.createQuery("from TravelReview where title=:titleId");
        query.setString("titleId", city);
        TravelReview review = (TravelReview) query.uniqueResult();
        System.out.println("Method {getTravelReviewWithQueryParam} results:" + review);tx.commit();

    }

    private void getTravelReviewWithQueryParamList() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        List titleList = new ArrayList();
        titleList.add("London");
        titleList.add("Venice");
        Query query = session.createQuery("from TravelReview where title in (:titleList)");
        query.setParameterList("titleList", titleList);;
        List<TravelReview> reviews = query.list();
        System.out.println("Method {getTravelReviewWithQueryParamList} results:" + reviews);

        tx.commit();

    }

    private void getTravelReviewWithSelect() {
        String SELECT_QUERY = "SELECT tr.review from TravelReview as tr";

        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        Query query = session.createQuery(SELECT_QUERY);
        List<String> reviews = query.list();
        System.out.println("Method {getTravelReviewWithSelect} results:");

        for (int i=0; i<reviews.size();i++) {
            System.out.println("\t" + reviews.get(i));
        }

        tx.commit();
    }

    private void getTravelReviewWithQuery() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        Query query = session.createQuery("from TravelReview where title='" + "London'");
        List<TravelReview> reviews = query.list();

        for (int i=0;i<reviews.size();i++) {
            System.out.println("Travel Review" + reviews.get(i));
        }

        tx.commit();
    }

    private void updateTravelReview() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        Query query = session.createQuery("update TravelReview set review=:review where id=21");
        query.setParameter("review", "The city with charm. The city you will never forget");
        int success = query.executeUpdate();
        System.out.println("Updated "+success);

        tx.commit();

    }

    private void deleteTravelReview() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        Query query = session.createQuery("delete TravelReview where id=23");
        int success = query.executeUpdate();
        System.out.println("delete success "+success);

        tx.commit();

    }

    private void populateReviews() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();

        tx.begin();

        List<TravelReview> reviews = createReviewSamples();

        for (int i=0;i<reviews.size();i++) {
            session.save(reviews.get(i));
        }

        tx.commit();

        System.out.println("Done");

    }

    private List<TravelReview> createReviewSamples() {
        List<TravelReview> reviews = new ArrayList<TravelReview>();
        TravelReview review = new TravelReview();

        review.setTitle("London");
        review.setReview("One of the best city you must visit before you die.");
        reviews.add(review);

        review = new TravelReview();
        review.setTitle("Austria");
        review.setReview("Sound of Music!.");
        reviews.add(review);

        review = new TravelReview();
        review.setTitle("Venice");
        review.setReview("Best island on water, although a bit expensive");
        reviews.add(review);

        review = new TravelReview();
        review.setTitle("New York");
        review.setReview("The city that never sleeps!");
        reviews.add(review);

        return reviews;
    }

    public static void main(String[] args) {
        QueryTest test = new QueryTest();
        test.init();
        //String titleId= JOptionPane.showInputDialog("Please enter a city")
        //test.populateReviews();
        //test.getAllTravelReviews();
        //test.getTravelReviewUniqueRecord();
        //  test.getTravelReviewWithQueryParam(JOptionPane.showInputDialog(null, "Please enter a city"));
        // test.getTravelReviewWithQueryParamList();
        // test.getTravelReviewWithSelect();
        //test.updateTravelReview();
         test.deleteTravelReview();

    }
}
