package org.example.domain.mapper;

import org.example.domain.dto.PaymentDto;
import org.example.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;

import static org.example.common.Constants.DATE_PATTERN;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {

   public PaymentDto toDto(Payment entity) {
       PaymentDto paymentDto = new PaymentDto();
       paymentDto.setUserId(entity.getUser().getId());
       paymentDto.setPaymentAmount(entity.getPaymentAmount());
       paymentDto.setBeforeBalance(entity.getBeforeBalance());
       paymentDto.setAfterBalance(entity.getAfterBalance());
       paymentDto.setPaymentDate(entity.getPaymentDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
       return paymentDto;
   }
}
