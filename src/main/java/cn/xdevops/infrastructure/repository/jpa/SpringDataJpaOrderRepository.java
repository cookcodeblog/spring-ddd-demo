package cn.xdevops.infrastructure.repository.jpa;

import cn.xdevops.infrastructure.repository.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}
