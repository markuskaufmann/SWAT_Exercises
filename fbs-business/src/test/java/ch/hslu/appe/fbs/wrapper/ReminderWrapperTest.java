package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.ReminderDTO;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Reminder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class ReminderWrapperTest {

    @Test
    public void dtoFromEntity_when_reminderEntity_then_reminderDTO() {
        // arrange
        final ReminderWrapper reminderWrapper = new ReminderWrapper();

        final int billId = 1;
        final int price = 5;
        final int orderId = 2;
        final Bill billEntity = new Bill();
        billEntity.setId(billId);
        billEntity.setPrice(price);
        billEntity.setOrderId(orderId);

        final int reminderId = 3;
        final Reminder reminderEntity = new Reminder();
        reminderEntity.setId(reminderId);
        reminderEntity.setBillId(billId);
        reminderEntity.setBillByBillId(billEntity);

        //act
        final ReminderDTO reminderDTO = reminderWrapper.dtoFromEntity(reminderEntity);
        final BillDTO billDTO = reminderDTO.getBillByBillId();

        //assert
        Assert.assertEquals(reminderId, reminderDTO.getId().intValue());
        Assert.assertEquals(billId, reminderDTO.getBillId().intValue());
        Assert.assertEquals(billId, billDTO.getId().intValue());
        Assert.assertEquals(price, billDTO.getPrice().intValue());
        Assert.assertEquals(orderId, billDTO.getOrderId().intValue());
    }

    @Test
    public void entityFromDTO_when_reminderDTO_then_reminderEntity() {
        // arrange
        final ReminderWrapper reminderWrapper = new ReminderWrapper();

        final int billId = 1;
        final int price = 5;
        final int orderId = 2;
        final BillDTO billDTO = new BillDTO(billId, orderId, price);

        final int reminderId = 3;
        final ReminderDTO reminderDTO = new ReminderDTO(reminderId, billId, billDTO);

        //act
        final Reminder reminderEntity = reminderWrapper.entityFromDTO(reminderDTO);

        //assert
        Assert.assertEquals(reminderId, reminderEntity.getId().intValue());
        Assert.assertEquals(billId, reminderEntity.getBillId().intValue());
    }
}
