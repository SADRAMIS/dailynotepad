package com.example.daily_notepad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            for (Task task : tasks) {
                if (task.getCreateDate() != null) {
                    task.setFormattedCreateDate(task.getCreateDate().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")));
                }
                if (task.getDueDate() != null) {
                    task.setFormattedDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")));
                }
            }
            model.addAttribute("tasks", tasks); // Передаем задачи с отформатированными датами
            return "taskList";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при получении задач: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/new")
    public String showNewTaskForm(Model model) {
        Task task = new Task();
        task.setCreateDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));
        model.addAttribute("task", task);
        model.addAttribute("commonTasks", getCommonTasks());
        return "taskForm";
    }
    private List<Task> getCommonTasks() {
        List<Task> commonTasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTitle("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setCreateDate(LocalDateTime.now());
        task1.setDueDate(LocalDateTime.now().plusDays(1));

        Task task2 = new Task();
        task2.setTitle("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setCreateDate(LocalDateTime.now());
        task2.setDueDate(LocalDateTime.now().plusDays(2));

        commonTasks.add(task1);
        commonTasks.add(task2);

        return commonTasks;
    }


    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task) {
        try {
            taskRepository.save(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении задачи: " + e.getMessage());
            return "error";
        }
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
            return "redirect:/tasks";
        } catch (Exception e) {
            System.err.println("Ошибка при удалении задачи: " + e.getMessage());
            return "error";
        }
    }
}
