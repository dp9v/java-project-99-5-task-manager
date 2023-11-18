package hexlet.code.service;

import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final TaskStatusRepository repository;

    public List<TaskStatus> getAll() {
        return repository.findAll();
    }

    public TaskStatus getById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public TaskStatus create(TaskStatusDTO statusTO) {
        var statusToCreate = merge(new TaskStatus(), statusTO)
                .setCreatedAt(LocalDate.now());
        return repository.save(statusToCreate);
    }

    public TaskStatus update(Long id, TaskStatusDTO statusTO) {
        var statusToCreate = repository.findById(id)
                .map(s -> merge(s, statusTO))
                .orElseThrow();
        return repository.save(statusToCreate);
    }

    private TaskStatus merge(TaskStatus target, TaskStatusDTO source) {
        source.getName().ifPresent(target::setName);
        source.getSlug().ifPresent(target::setSlug);
        return target;
    }
}
