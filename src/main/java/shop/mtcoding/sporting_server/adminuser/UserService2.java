package shop.mtcoding.sporting_server.adminuser;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService2 {

    private final UserRepository userRepository;

    // public Page<User> getUserList(Pageable pageable) {

    // return userRepository.findAll(pageable);
    // }

    // public Page<User> getUserList(Pageable pageable) {
    // Specification<User> spec = (root, query, cb) -> {
    // return cb.equal(root.get("role"), "player");
    // };
    // return userRepository.findAll(spec, pageable);
    // }

    public Page<User> getPlayerUserList(Pageable pageable) {
        User user = new User();
        user.setRole("PLAYER");
        user.setStatus(UserStatus.일반회원);
        Example<User> example = Example.of(user);

        return userRepository.findAll(example, pageable);
    }

    public Page<User> getCompanyUserList(Pageable pageable) {
        User user = new User();
        user.setRole("COMPANY");
        user.setStatus(UserStatus.기업회원);
        Example<User> example = Example.of(user);

        return userRepository.findAll(example, pageable);
    }

    public Page<User> getWaitUserList(Pageable pageable) {
        User user = new User();
        user.setRole("COMPANY");
        user.setStatus(UserStatus.인증대기);
        Example<User> example = Example.of(user);

        return userRepository.findAll(example, pageable);
    }

    public Page<User> getUserListByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable);
    }

    public boolean approveCompany(Long userId) {
        Optional<User> User = userRepository.findById(userId);
        if (User.isPresent()) {
            User user = User.get();
            user.setStatus(UserStatus.기업회원);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
