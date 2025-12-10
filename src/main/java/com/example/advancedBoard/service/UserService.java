package com.example.advancedBoard.service;

import com.example.advancedBoard.dto.request.UserCreateRequest;
import com.example.advancedBoard.dto.request.UserUpdateRequest;
import com.example.advancedBoard.dto.response.UserResponse;
import com.example.advancedBoard.entity.User;
import com.example.advancedBoard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 생성
     */
    public UserResponse createUser(UserCreateRequest request) {
        // 중복 확인
        validateEmailDuplication(request.getEmail());
        validateUsernameDuplication(request.getUsername());

        // Request -> Entity 변환
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // 실제로는 암호화 필요
                .build();

        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    /**
     * 사용자 조회 (ID)
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = findUserEntityById(id);
        return convertToResponse(user);
    }

    /**
     * 사용자 조회 (이메일)
     */
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다: " + email));

        return convertToResponse(user);
    }

    /**
     * 전체 사용자 조회
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 사용자 수정
     */
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = findUserEntityById(id);

        // 사용자명 수정
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            // 변경하는 경우에만 중복 확인
            if (!user.getUsername().equals(request.getUsername())) {
                validateUsernameDuplication(request.getUsername());
            }
            user.updateUsername(request.getUsername());
        }

        // 비밀번호 수정
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.updatePassword(request.getPassword()); // 실제로는 암호화 필요
        }

        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    /**
     * 사용자 삭제 (논리적 삭제 - 상태 변경)
     */
    public void deleteUser(Long id) {
        User user = findUserEntityById(id);
        user.delete(); // 상태를 DELETED로 변경
        userRepository.save(user);
    }

    /**
     * 사용자 차단
     */
    public UserResponse blockUser(Long id) {
        User user = findUserEntityById(id);
        user.block();
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    /**
     * 사용자 활성화
     */
    public UserResponse activateUser(Long id) {
        User user = findUserEntityById(id);
        user.activate();
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    // ========== Private 헬퍼 메서드 ==========

    /**
     * 이메일 중복 확인
     */
    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + email);
        }
    }

    /**
     * 사용자명 중복 확인
     */
    private void validateUsernameDuplication(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + username);
        }
    }

    /**
     * Entity -> Response 변환
     */
    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // ========== Public 헬퍼 메서드 (다른 Service에서 사용) ==========

    /**
     * User Entity 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. ID: " + id));
    }
}
