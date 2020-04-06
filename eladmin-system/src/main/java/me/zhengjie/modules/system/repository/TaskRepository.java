package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
* @author zzy
* @date 2020-04-02
*/
@Component
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}