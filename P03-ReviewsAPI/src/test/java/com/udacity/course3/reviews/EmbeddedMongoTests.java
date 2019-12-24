package com.udacity.course3.reviews;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
//@ExtendWith(SpringExtension.class)
public class EmbeddedMongoTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void mongoDBTest() {

        DBObject dbObject = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        mongoTemplate.save(dbObject, "myCollection");

        assertThat(mongoTemplate.findAll(DBObject.class, "myCollection")).extracting("key")
                .containsOnly("value");
    }
}
