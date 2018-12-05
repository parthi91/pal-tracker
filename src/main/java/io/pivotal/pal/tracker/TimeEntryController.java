package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RestController
public class TimeEntryController {
    TimeEntryRepository repository;
    List<TimeEntry> entries = new ArrayList<>();

    public TimeEntryController(TimeEntryRepository repository) {
        this.repository=repository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {

        //entries.add(timeEntry);
        return new ResponseEntity(repository.create(timeEntry), HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        /*ListIterator<TimeEntry> it = entries.listIterator();
        while(it.hasNext()) {
            TimeEntry time =it.next();
            if(time.getId() == id) {
                return ResponseEntity.ok(time);
            }

        }*/
        TimeEntry entry = repository.find(id);
        if(entry != null) {
           return new ResponseEntity(entry, HttpStatus.OK);
        }
        else {
            return  new ResponseEntity(entry,HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {

       /* ListIterator<TimeEntry> it = entries.listIterator();
        while(it.hasNext()) {
            TimeEntry time =it.next();
            if(time.getId() == id) {
                it.remove();
                it.add(timeEntry);
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }

        }*/
        TimeEntry entry = repository.update(id, timeEntry);
        if(entry != null) {
            return new ResponseEntity(entry, HttpStatus.OK);
       }
        else {
            return new ResponseEntity(entry, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>(repository.list(), HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id){
      /*  ListIterator<TimeEntry> it = entries.listIterator();
        while(it.hasNext()) {
            TimeEntry time =it.next();
            if(time.getId() == id) {
                it.remove();
                return ResponseEntity.ok(time);
            }

        }*/

        return new ResponseEntity(repository.delete(id),HttpStatus.NO_CONTENT);
    }

}
