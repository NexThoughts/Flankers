package com.services

import com.model.User
import com.verticle.ApiVerticle
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.RoutingContext

import java.util.concurrent.CountDownLatch

class BootstrapService {

    static MongoClient mongoClient = ApiVerticle.mongoClient

    void startBootstrap() {
        println "*********** AT Start Bootstrap **************"

//        CountDownLatch countDownLatch = new CountDownLatch(1)
//        User user
//        mongoClient.findOne("user", new JsonObject().put("id", (36 as Integer)), null, { ar ->
//
////            if (ar.succeeded()) {
//            user = new User(ar.result())
////            countDownLatch.countDown()
////            }
//        })

//        waitForResult(countDownLatch)
        createUsers()
        createTag()
        createTodo()
        createTeam()
        createComments()


    }

    void waitForResult(CountDownLatch countDownLatch) {

        try {
            countDownLatch.await()
        } catch (Exception e) {
        }
    }

    void createUsers() {

        println "************* Creating Users *************"

        int totalUsers = 40

        Random r = new Random();
        int Low = 10;
        int High = 100;
        int Result = r.nextInt(High - Low) + Low;

        List<String> firstNames = ["chris", "ab", "steve", "james", "jonny", "andre", "dj", "hashim", "quinton", "max", "lionel", "faf", "kangiso", "Tinu", "daniel", "David"]
        List<String> lastNames = ["Gayle", "Smith", "Anderson", "Russel", "Messi", "devillers", "Best", "Miller"]

        while (totalUsers > 0) {
            int firstNameIndex = r.nextInt(firstNames.size() - 0) + 0
            int lastNameIndex = r.nextInt(lastNames.size() - 0) + 0

            String fname = firstNames.get(firstNameIndex)
            String lname = lastNames.get(lastNameIndex)
            String email = "${fname}${lname}@fintechlabs.in"
            String password = email
            String gender = ((r.nextInt(2 - 0) + 0) == 1) ? "Male" : "Female"
            String location = ((r.nextInt(2 - 0) + 0) == 1) ? "India" : "Australia"

            Map m = [id: totalUsers, firstname: fname, lastname: lname, email: email, gender: gender, location: location, password: password]

            JsonObject jsonObject = new JsonObject()
            m.each { k, v ->
                jsonObject.put(k.toString(), v)
            }

            saveInCollection("user", jsonObject)

            totalUsers = totalUsers - 1
        }


    }

    void saveInCollection(String collectionName, JsonObject jsonObject) {
        mongoClient.insert(collectionName, jsonObject, {})
    }

    void createTag() {

        List<String> appenders = ["Least", "Low", "High", "Medium", "Extreme"]
        List<String> tags = ["Urgent", "Prior", "Important", "Today", "TisWeek", "Duplicate"]

        List<String> firstNames = ["chris", "ab", "steve", "james", "jonny", "andre", "dj", "hashim", "quinton", "max", "lionel", "faf", "kangiso", "Tinu", "daniel", "David"]


        appenders.eachWithIndex { appender, index ->
            tags.eachWithIndex { tag, ind ->

                User user
                mongoClient.findOne("user", new JsonObject().put("id", (ind + index)), null, { ar ->
                    if (ar.succeeded()) {
                        user = new User(ar.result())
                    }
                })

                Map m = [id: (index + ind).toString(), name: "${appender}${tag}", createdBy: (index + ind).toString()]

                JsonObject jsonObject = new JsonObject()
                m.each { k, v ->
                    jsonObject.put(k.toString(), v)
                }

                saveInCollection("tag", jsonObject)


            }

        }


    }

    void createTodo() {

        List<String> operation = ["Create ", "Delete ", "Fix ", "Change UI "]
        List<String> entity = ["User", "Admin", "user dashboard", "Admin Dashboard", "Theme", "left Navigation"]

        operation.eachWithIndex { op, index ->
            entity.eachWithIndex { en, ind ->

                User user
                mongoClient.findOne("user", new JsonObject().put("id", (ind + index)), null, { ar ->
                    if (ar.succeeded()) {
                        user = new User(ar.result())
                    }
                })

                Map m = [id: (index + ind).toString(), name: "${op}${en}", createdBy: (ind + index).toString()]

                JsonObject jsonObject = new JsonObject()
                m.each { k, v ->
                    jsonObject.put(k.toString(), v)
                }

                saveInCollection("todo", jsonObject)


            }

        }


    }

    void createTeam() {

        int totalTeams = 5

        while (totalTeams > 0) {

            List<String> teamNames = ["Flankers", "Invincibles", "South Africa", "Australia", "West Indies"]
            teamNames.eachWithIndex { name, index ->


                List<String> users = [index.toString(), (index + 1).toString(), (index + 2).toString()]

                Map m = [id: index, name: name, users: users]

                JsonObject jsonObject = new JsonObject()
                m.each { k, v ->
                    jsonObject.put(k.toString(), v)
                }

                saveInCollection("team", jsonObject)

            }

            totalTeams = totalTeams - 1
        }

    }


    void createComments() {

        List<String> operation = ["Create ", "Delete ", "Fix ", "Change UI "]
        List<String> entity = ["User", "Admin", "user dashboard", "Admin Dashboard", "Theme", "left Navigation"]

        operation.eachWithIndex { op, index ->
            entity.eachWithIndex { en, ind ->


                Map m = [id: (ind + index).toString(), message: "Working on ${op} ${en}", createdBy: (ind + index).toString(), todoId: (ind + index).toString()]

                JsonObject jsonObject = new JsonObject()
                m.each { k, v ->
                    jsonObject.put(k.toString(), v)
                }

                saveInCollection("comment", jsonObject)


            }
        }

    }


}
