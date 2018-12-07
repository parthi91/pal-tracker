package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
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
    TimeEntryRepository timeEntriesRepo;
    List<TimeEntry> entries = new ArrayList<>();
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository repository, MeterRegistry meterRegistry) {
        this.timeEntriesRepo=repository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {

        //entries.add(timeEntry);
        //return new ResponseEntity(repository.create(timeEntry), HttpStatus.CREATED);
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntriesRepo.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
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
        /*TimeEntry entry = repository.find(id);
        if(entry != null) {
           return new ResponseEntity(entry, HttpStatus.OK);
        }
        else {
            return  new ResponseEntity(entry,HttpStatus.NOT_FOUND);
        }*/
        TimeEntry timeEntry = timeEntriesRepo.find(id);
        if (timeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
       /* TimeEntry entry = repository.update(id, timeEntry);
        if(entry != null) {
            return new ResponseEntity(entry, HttpStatus.OK);
       }
        else {
            return new ResponseEntity(entry, HttpStatus.NOT_FOUND);
        }*/
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(id, timeEntry);
        if (updatedTimeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        //return new ResponseEntity<List<TimeEntry>>(repository.list(), HttpStatus.OK);
        actionCounter.increment();
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id){
      /*  ListIterator<TimeEntry> it = entries.listIterator();
        while(it.hasNext()) {
            TimeEntry time =it.next();
            if(time.getId() == id) {
                it.remove();
                return ResponseEntity.ok(time);
            }

        }*/
        /*repository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);*/
        timeEntriesRepo.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntriesRepo.list().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
