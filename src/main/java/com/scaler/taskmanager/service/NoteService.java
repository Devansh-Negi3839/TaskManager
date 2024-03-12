package com.scaler.taskmanager.service;

import com.scaler.taskmanager.entities.NoteEntity;
import com.scaler.taskmanager.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NoteService {
    private final TaskService taskService;
    private final HashMap<Integer, TaskNotesHolder> taskNotesHolders=new HashMap<>();
    public NoteService(TaskService taskService){
        this.taskService=taskService;
    }

    static class TaskNotesHolder{
        protected int noteId=1;
        protected ArrayList<NoteEntity> notes=new ArrayList<>();
    }

    public List<NoteEntity> getNotesForTask(int taskId){
        TaskEntity task=taskService.getTaskById(taskId);
        if(task==null)
            return null;
        if(taskNotesHolders.get(taskId)==null)
            taskNotesHolders.put(taskId, new TaskNotesHolder());
        return taskNotesHolders.get(taskId).notes;
    }

    public NoteEntity addNoteForTask(int taskId, String title, String body) {
        TaskEntity task = taskService.getTaskById(taskId);
        if (task == null) {
            return null;
        }
        if (taskNotesHolders.get(taskId) == null) {
            taskNotesHolders.put(taskId, new TaskNotesHolder());
        }
        TaskNotesHolder taskNotesHolder = taskNotesHolders.get(taskId);
        NoteEntity note = new NoteEntity();
        note.setId(taskNotesHolder.noteId);
        note.setTitle(title);
        note.setBody(body);
        taskNotesHolder.notes.add(note);
        taskNotesHolder.noteId++;
        return note;
    }


    public List<NoteEntity> deleteAllNotesForTaskId(int taskId) {
        TaskEntity task = taskService.getTaskById(taskId);
        if (task == null || taskNotesHolders.get(taskId) == null)
            return null;

        List<NoteEntity> deletedNotes = taskNotesHolders.get(taskId).notes;
        taskNotesHolders.remove(taskId); // Remove the notes holder for the task ID
        return deletedNotes;
    }

}
