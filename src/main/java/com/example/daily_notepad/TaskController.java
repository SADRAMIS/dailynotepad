package com.example.daily_notepad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String getAllTasks(Model model) {
        try {
            List<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
            return "taskList";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при получении задач: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/new")
    public String showNewTaskForm(Model model){
        model.addAttribute("task", new Task());
        return "taskForm";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении задачи: " + e.getMessage());
            return "error";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);
        return "taskForm";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        task.setId(id);
        return saveTask(task); // Переиспользуем метод сохранения
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Ошибка при удалении задачи: " + e.getMessage());
            return "error";
        }
        return "redirect:/tasks";
    }
}
