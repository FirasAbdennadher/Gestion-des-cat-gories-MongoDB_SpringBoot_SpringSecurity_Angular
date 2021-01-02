package org.sid.dao;

import org.sid.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController

public interface ProductRepository extends MongoRepository<Product,String> {


}
