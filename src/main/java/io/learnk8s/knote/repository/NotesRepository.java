package io.learnk8s.knote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.learnk8s.knote.entity.Note;

public interface NotesRepository extends MongoRepository<Note, String> {
    
}
