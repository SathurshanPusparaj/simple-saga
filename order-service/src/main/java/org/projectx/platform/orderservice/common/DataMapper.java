package org.projectx.platform.orderservice.common;

import org.projectx.platform.orderservice.dto.OrderDTO;
import org.projectx.platform.orderservice.dto.TrackerDTO;
import org.projectx.platform.orderservice.dto.UserDTO;
import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.entity.OrderEntity;
import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.entity.UserEntity;

public class DataMapper {

    public final static UserDTO convertUserEntityToUserDTO(UserEntity userEntity) {
        return new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public final static UserEntity convertUserDTOToUserEntity(UserDTO user) {
        return new UserEntity(user.getName(), user.getEmail());
    }

    public final static OrderEntity convertOrderDTOToOrderEntity(OrderDTO orderDTO) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(orderDTO.getItemId());
        OrderEntity order = new OrderEntity(new UserEntity(orderDTO.getUserId()), itemEntity,
                orderDTO.getNumberOfItems(),
                orderDTO.getTotalPrice(),
                orderDTO.getTrackerDTO() != null ?
                        new TrackerEntity(orderDTO.getTrackerDTO().getId(), orderDTO.getTrackerDTO().getStatus()) : null);
        return order;
    }

    public final static OrderDTO convertOrderEntityToOrderDTO(OrderEntity order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTrackerDTO(new TrackerDTO(order.getTrackingId().getId(), order.getTrackingId().getStatus()));
        orderDTO.setItemId(order.getItemEntity().getId());
        orderDTO.setUserId(order.getUserEntity().getId());
        orderDTO.setNumberOfItems(order.getNumberOfItems());
        orderDTO.setTotalPrice(order.getTotalPrice());
        return orderDTO;
    }
}
