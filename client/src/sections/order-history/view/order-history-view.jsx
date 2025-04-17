import { useEffect, useState } from 'react';

import Card from '@mui/material/Card';
import Stack from '@mui/material/Stack';
import Table from '@mui/material/Table';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TableBody from '@mui/material/TableBody';
import Typography from '@mui/material/Typography';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';

import { users } from 'src/_mock/user';

import Iconify from 'src/components/iconify';
import Scrollbar from 'src/components/scrollbar';

import TableNoData from '../table-no-data';
import UserTableRow from '../order-table-row';
import UserTableHead from '../order-table-head';
import TableEmptyRows from '../table-empty-rows';
import UserTableToolbar from '../order-table-toolbar';
import { emptyRows, applyFilter, getComparator } from '../utils';
import OrderTablePage from './order-view-table';
import { Box, IconButton, Modal } from '@mui/material';
import { OrderView } from 'src/sections/order/view';
import { getOrders } from 'src/services/orderService';

// ----------------------------------------------------------------------

export default function OrderHistoryPage() {
  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [selected, setSelected] = useState([]);

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [showModal, setShowModal] = useState(false);
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);


  const handleSort = (event, id) => {
    const isAsc = orderBy === id && order === 'asc';
    if (id !== '') {
      setOrder(isAsc ? 'desc' : 'asc');
      setOrderBy(id);
    }
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelecteds = users.map((n) => n.name);
      setSelected(newSelecteds);
      return;
    }
    setSelected([]);
  };

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleOrderPlacedSuccess = () => {
    fetchData(); // Refresh the product list
    handleCloseModal(); // Close the modal
  };

  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, name);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }
    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setPage(0);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const handleFilterByName = (event) => {
    setPage(0);
    setFilterName(event.target.value);
  };

  const fetchData = async () => {
        setLoading(true);
        try {
          const response = await getOrders();// Replace with your API endpoint
          // if (!response.ok) {
          //   throw new Error('Failed to fetch data');
          // }
          // const result = await response.json();
          console.log(response?.data?.data);
          setData(response.data.data);
        } catch (err) {
          setError(err.message);
        } finally {
          setLoading(false);
        }
      };

  useEffect(() => {
    fetchData();
  }, []);




  return (
    <Container maxWidth="l">
      <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
        <Typography variant="h4">Order history</Typography>

        <Button variant="contained" color="inherit" startIcon={<Iconify icon="eva:plus-fill" />} onClick={handleOpenModal}>
          New Order
        </Button>
      </Stack>

      <OrderTablePage data={data}/>

      <Modal
      open={showModal} onClose={handleCloseModal} aria-labelledby="add-product-modal">
        <Box
          sx={{
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            bgcolor: 'background.paper',
            // boxShadow: 24,
            p: 3,
            width: '90%',
            maxWidth: 1200,
            borderRadius: 2,
          }}
        >
          <Stack direction="row" justifyContent="space-between" alignItems="center" mb={2}>
            <Typography id="add-product-modal" variant="h6" component="h2" >
              Place order
            </Typography>
            <IconButton onClick={handleCloseModal}>
            <Iconify icon="eva:close-outline" />
            </IconButton>
          </Stack>
          {/* <Typography>Hello order something</Typography> */}
          <OrderView onSuccess={handleOrderPlacedSuccess}></OrderView>
          {/* <AddProductView onSuccess={handleProductAddedSuccess}/> */}
        </Box>
      </Modal>
    </Container>
  );
}
