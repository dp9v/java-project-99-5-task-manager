package hexlet.code.utils;

import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ModelGenerator {
    private Model<User> userModel;
    private Model<UserDTO> userTOModel;

    private Model<TaskStatus> taskStatusModel;
    private Model<TaskStatusDTO> taskStatusTOModel;

    private Model<Task> taskModel;
    private Model<TaskDTO> taskTOModel;

    private Model<Label> labelModel;
    private Model<LabelDTO> labelTOModel;

    @PostConstruct
    private void init() {
        var faker = new Faker();
        userModel = Instancio.of(User.class)
            .ignore(Select.field(User::getId))
            .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
            .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
            .supply(Select.field(User::getLastName), () -> faker.name().lastName())
            .supply(Select.field(User::getPassword), () -> faker.internet().password())
            .toModel();

        userTOModel = Instancio.of(UserDTO.class)
            .ignore(Select.field(UserDTO::createdAt))
            .ignore(Select.field(UserDTO::updatedAt))
            .ignore(Select.field(UserDTO::id))
            .supply(Select.field(UserDTO::email), () -> faker.internet().emailAddress())
            .supply(Select.field(UserDTO::firstName), () -> faker.name().firstName())
            .supply(Select.field(UserDTO::lastName), () -> faker.name().lastName())
            .supply(Select.field(UserDTO::password), () -> faker.internet().password())
            .toModel();

        taskStatusModel = Instancio.of(TaskStatus.class)
            .ignore(Select.field(TaskStatus::getId))
            .supply(Select.field(TaskStatus::getName), () -> faker.animal().name())
            .supply(Select.field(TaskStatus::getSlug), () -> faker.internet().slug())
            .toModel();

        taskStatusTOModel = Instancio.of(TaskStatusDTO.class)
            .ignore(Select.field(TaskStatusDTO::id))
            .ignore(Select.field(TaskStatusDTO::createdAt))
            .supply(Select.field(TaskStatusDTO::name), () -> faker.animal().name())
            .supply(Select.field(TaskStatusDTO::slug), () -> faker.internet().slug())
            .toModel();

        taskModel = Instancio.of(Task.class)
            .ignore(Select.field(Task::getId))
            .supply(Select.field(Task::getName), () -> faker.animal().name())
            .supply(Select.field(Task::getDescription), () -> faker.animal().scientificName())
            .toModel();

        taskTOModel = Instancio.of(TaskDTO.class)
            .ignore(Select.field(TaskDTO::id))
            .supply(Select.field(TaskDTO::title), () -> faker.animal().name())
            .supply(Select.field(TaskDTO::content), () -> faker.animal().scientificName())
            .toModel();

        labelModel = Instancio.of(Label.class)
            .ignore(Select.field(Label::getId))
            .supply(Select.field(Label::getName), () -> faker.beer().brand())
            .toModel();

        labelTOModel = Instancio.of(LabelDTO.class)
            .ignore(Select.field(LabelDTO::id))
            .supply(Select.field(LabelDTO::name), () -> faker.beer().brand())
            .toModel();
    }
}
