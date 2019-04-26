package ch.hslu.appe.fbs.remote.service.customer;

import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.customer.CustomerManager;
import ch.hslu.appe.fbs.business.order.OrderManager;
import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.common.dto.OrderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.CustomerService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public final class CustomerServiceImpl implements CustomerService {

    private static final String ERROR_NULL_OBJ_REFERENCE = "object reference can't be null";

    private final ClientHost clientHost;
    private final UserSessionMap userSessionMap;

    private final CustomerManager customerManager;
    private final OrderManager orderManager;
    private final BillManager billManager;

    public CustomerServiceImpl(final ClientHost clientHost, final UserSessionMap userSessionMap, final CustomerManager customerManager,
                               final OrderManager orderManager, final BillManager billManager) {
        this.clientHost = clientHost;
        this.userSessionMap = userSessionMap;
        this.customerManager = customerManager;
        this.orderManager = orderManager;
        this.billManager = billManager;
    }

    @Override
    public CustomerDTO getCustomer(final int customerId) throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = getUserSession();
        return this.customerManager.getCustomer(customerId, userDTO);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = getUserSession();
        return this.customerManager.getAllCustomers(userDTO);
    }

    @Override
    public List<OrderDTO> getOrders(final int customerId) throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = getUserSession();
        return this.orderManager.getOrders(customerId, userDTO);
    }

    @Override
    public List<BillDTO> getRemindedBills(final int customerId) throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = getUserSession();
        return this.billManager.getRemindedBillsByCustomerId(customerId, userDTO);
    }

    @Override
    public void createOrder(final OrderDTO orderDTO) throws RemoteException, UserNotAuthorisedException {
        if (orderDTO == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        final UserDTO userDTO = getUserSession();
        this.orderManager.createOrder(orderDTO, userDTO);
    }

    @Override
    public void createCustomer(final CustomerDTO customerDTO) throws RemoteException, UserNotAuthorisedException {
        if (customerDTO == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        final UserDTO userDTO = getUserSession();
        this.customerManager.createCustomer(customerDTO, userDTO);
    }

    @Override
    public void cancelOrder(final OrderDTO orderDTO) throws RemoteException, UserNotAuthorisedException {
        if (orderDTO == null) {
            throw new IllegalArgumentException(ERROR_NULL_OBJ_REFERENCE);
        }
        final UserDTO userDTO = getUserSession();
        this.orderManager.cancelOrder(orderDTO, userDTO);
    }

    @Override
    public List<OrderDTO> getAllOrders() throws RemoteException, UserNotAuthorisedException {
        final UserDTO userDTO = getUserSession();
        return this.orderManager.getAllOrders(userDTO);
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getCustomerServiceName();
    }

    private UserDTO getUserSession() {
        final Optional<UserDTO> optUserDTO = this.userSessionMap.getUserSession(this.clientHost.getHostAddress());
        if(optUserDTO.isPresent()) {
            return optUserDTO.get();
        } else {
            throw new IllegalStateException("No valid user session found");
        }
    }
}
