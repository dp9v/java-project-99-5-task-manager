package hexlet.code.service;

import hexlet.code.dto.UserDTO;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User create(UserDTO user) {
        var userToCreate = merge(new User(), user)
            .setCreatedAt(LocalDate.now());

        return userRepository.save(userToCreate);
    }

    public User update(Long id, UserDTO user) {
        var userToUpdate = merge(userRepository.findById(id).orElseThrow(), user);

        return userRepository.save(userToUpdate);
    }

    private User merge(User user, UserDTO userDTO) {
        userDTO.getEmail().ifPresent(user::setEmail);
        userDTO.getLastName().ifPresent(user::setLastName);
        userDTO.getFirstName().ifPresent(user::setFirstName);
        userDTO.getPassword().ifPresent(p -> user.setPassword(
            passwordEncoder.encode(p)
        ));
        return user.setUpdatedAt(LocalDate.now());
    }

    @Override
    public void createUser(UserDetails user) {
        userRepository.save(new User()
            .setEmail(user.getUsername())
            .setPassword(passwordEncoder.encode(user.getPassword()))
        );
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public boolean userExists(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'userExists'");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("Username '%s' was not found", username)));
    }
}