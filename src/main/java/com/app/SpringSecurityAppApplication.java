package com.app;

import com.app.persistence.entity.PermissionEntity;
import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityAppApplication.class, args);
  }

  @Bean
  CommandLineRunner init(UserRepository userRepo) {
    return args -> {
      //Permissions
      PermissionEntity createPermission = PermissionEntity
              .builder()
              .name("CREATE")
              .build();
      PermissionEntity readPermission = PermissionEntity
              .builder()
              .name("READ")
              .build();
      PermissionEntity updatePermission = PermissionEntity
              .builder()
              .name("UPDATE")
              .build();
      PermissionEntity deletePermission = PermissionEntity
              .builder()
              .name("DELETE")
              .build();
      PermissionEntity refactorPermission = PermissionEntity
              .builder()
              .name("REFACTOR")
              .build();
      //Roles
      RoleEntity roleAdmin = RoleEntity
              .builder()
              .roleEnum(RoleEnum.ADMIN)
              .permissionList(Set.of(createPermission, readPermission, deletePermission, updatePermission))
              .build();

      RoleEntity roleUser = RoleEntity
              .builder()
              .roleEnum(RoleEnum.USER)
              .permissionList(Set.of(createPermission, readPermission))
              .build();

      RoleEntity roleInvited = RoleEntity
              .builder()
              .roleEnum(RoleEnum.INVITED)
              .permissionList(Set.of(readPermission))
              .build();

      RoleEntity roleDeveloper = RoleEntity
              .builder()
              .roleEnum(RoleEnum.DEVELOPER)
              .permissionList(Set.of(createPermission, readPermission, deletePermission, updatePermission, refactorPermission))
              .build();
      //Users
      UserEntity joe = UserEntity.builder()
                                   .username("joe")
                                   .password("$2a$10$gDDsRpHls.UW.18K6umBYuBRYi8VZjByLzsZVn4wJABWYdn7v9ibq")
                                   .isEnabled(true)
                                   .accountNoExpired(true)
                                   .accountNoLocked(true)
                                   .credentialNoExpired(true)
                                   .roles(Set.of(roleAdmin))
                                   .build();

      UserEntity mary = UserEntity.builder()
                                   .username("mary")
                                   .password("$2a$10$gDDsRpHls.UW.18K6umBYuBRYi8VZjByLzsZVn4wJABWYdn7v9ibq")
                                   .isEnabled(true)
                                   .accountNoExpired(true)
                                   .accountNoLocked(true)
                                   .credentialNoExpired(true)
                                   .roles(Set.of(roleUser))
                                   .build();

      UserEntity helen = UserEntity.builder()
                                   .username("helen")
                                   .password("$2a$10$gDDsRpHls.UW.18K6umBYuBRYi8VZjByLzsZVn4wJABWYdn7v9ibq")
                                   .isEnabled(true)
                                   .accountNoExpired(true)
                                   .accountNoLocked(true)
                                   .credentialNoExpired(true)
                                   .roles(Set.of(roleInvited))
                                   .build();

      UserEntity peter = UserEntity.builder()
                                   .username("peter")
                                   .password("$2a$10$gDDsRpHls.UW.18K6umBYuBRYi8VZjByLzsZVn4wJABWYdn7v9ibq")
                                   .isEnabled(true)
                                   .accountNoExpired(true)
                                   .accountNoLocked(true)
                                   .credentialNoExpired(true)
                                   .roles(Set.of(roleDeveloper))
                                   .build();
      userRepo.saveAll(List.of(joe, mary, helen, peter));
    };
  }
}
