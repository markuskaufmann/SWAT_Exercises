package ch.hslu.appe.fbs.remote.service.item;

import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ItemService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.service.customer.CustomerServiceImpl;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;
import ch.hslu.appe.fbs.wrapper.BillWrapper;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;
import ch.hslu.appe.fbs.wrapper.ItemWrapper;
import ch.hslu.appe.fbs.wrapper.OrderWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public final class ItemServiceImplTest {

    private static final String LOCALHOST = "localhost";

    @Mock
    private ItemManager itemManager;

    @Mock
    private ClientHost clientHost;

    @Mock
    private UserSessionMap userSessionMap;

    private ItemService itemService;
    private List<ItemDTO> itemTestees;

    @Before
    public void setUp() throws UserNotAuthorisedException {
        MockitoAnnotations.initMocks(this);
        this.itemService = new ItemServiceImpl(this.clientHost, this.userSessionMap, this.itemManager);
        this.itemTestees = getItemTestees();
        final UserDTO userTestee = getUserTestee();
        when(this.clientHost.getHostAddress()).thenReturn(LOCALHOST);
        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(userTestee));
        when(this.itemManager.getAllItems(userTestee)).thenReturn(this.itemTestees);
    }

    @Test
    public void getAllItems_WhenItemsExist_ThenReturnThemAll() throws RemoteException, UserNotAuthorisedException {
        final List<ItemDTO> items = this.itemService.getAllItems();
        assertEquals(this.itemTestees.size(), items.size());
    }

    @Test
    public void getServiceName_WhenServiceNameExists_ThenReturnIt() throws RemoteException {
        final String serviceName = this.itemService.getServiceName();
        assertEquals(RmiLookupTable.getItemServiceName(), serviceName);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SYSADMIN.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private List<ItemDTO> getItemTestees() {
        final List<ItemDTO> itemTestees = new ArrayList<>();
        final ItemWrapper itemWrapper = new ItemWrapper();

        final Item testee1 = new Item();
        testee1.setId(1);
        testee1.setName("testee1");
        testee1.setArtNr("testee1ArtNr");
        testee1.setPrice(7);
        testee1.setLocalStock(10);
        testee1.setMinLocalStock(5);
        testee1.setVirtualLocalStock(5);
        testee1.setReordersById(new ArrayList<>());
        itemTestees.add(itemWrapper.dtoFromEntity(testee1));

        final Item testee2 = new Item();
        testee2.setId(2);
        testee2.setName("testee2");
        testee2.setArtNr("testee2ArtNr");
        testee2.setPrice(14);
        testee2.setLocalStock(20);
        testee2.setMinLocalStock(10);
        testee2.setVirtualLocalStock(10);
        testee2.setReordersById(new ArrayList<>());
        itemTestees.add(itemWrapper.dtoFromEntity(testee2));

        final Item testee3 = new Item();
        testee3.setId(3);
        testee3.setName("testee3");
        testee3.setArtNr("testee3ArtNr");
        testee3.setPrice(28);
        testee3.setLocalStock(30);
        testee3.setMinLocalStock(20);
        testee3.setVirtualLocalStock(20);
        testee3.setReordersById(new ArrayList<>());
        itemTestees.add(itemWrapper.dtoFromEntity(testee3));

        return itemTestees;
    }
}
