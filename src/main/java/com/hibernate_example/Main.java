package com.hibernate_example;

import static spark.Spark.*;

import com.hibernate_example.models.Ability;
import com.hibernate_example.models.Pokemon;

import com.google.gson.Gson;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        staticFiles.location("/public");

        get("/api/pokemon", (req, res) -> {
            EntityManager session = sf.createEntityManager();
            res.type("application/json");

            try {
                List<Pokemon> pokemon = session.createQuery("FROM Pokemon").getResultList();
                return new Gson().toJsonTree(pokemon);
            } catch (Exception e) {
                res.status(500);
                return "{\"error\": \"oh no\"}";
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        });

        get("/api/pokemon/:name", (req, res) -> {
            EntityManager session = sf.createEntityManager();
            res.type("application/json");

            try {
                String name = req.params(":name");
                String q = "FROM Pokemon p WHERE p.name = :name";
                Query query = session.createQuery(q);
                query.setParameter("name", name);
                Pokemon pokemon = (Pokemon) query.getSingleResult();
                return new Gson().toJson(pokemon);
            } catch (Exception e) {
                res.status(500);
                return "{\"error\": \"oh no\"}";
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        });

        get("/api/pokemon/:name/abilities", (req, res) -> {
            EntityManager session = sf.createEntityManager();
            res.type("application/json");

            try {
                String name = req.params(":name");
                String q = "FROM Pokemon p WHERE p.name = :name";
                Query query = session.createQuery(q);
                query.setParameter("name", name);
                Pokemon pokemon = (Pokemon) query.getSingleResult();
                return new Gson().toJsonTree(pokemon.getAbilities());
            } catch (Exception e) {
                res.status(500);
                return "{\"error\": \"oh no\"}";
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        });

        post("/api/pokemon", (req, res) -> {
            EntityManager session = sf.createEntityManager();
            res.type("application/json");

            try {
                Pokemon pokemon = new Gson().fromJson(req.body(), Pokemon.class);

                session.getTransaction().begin();
                session.persist(pokemon);
                session.getTransaction().commit();

                res.status(201);
                return String.format("{\"id\": %d}", pokemon.getId());
            } catch (Exception e) {
                res.status(500);
                return "{\"error\": \"oh no\"}";
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        });
    }
}