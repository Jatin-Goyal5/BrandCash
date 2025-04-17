import { useEffect, useState } from 'react';

import Stack from '@mui/material/Stack';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

// import { products } from 'src/_mock/products';

import ProductCard from '../product-card';
import ProductSort from '../product-sort';
import ProductFilters from '../product-filters';
import ProductCartWidget from '../product-cart-widget';
import { Box, Button, IconButton, Modal } from '@mui/material';
import Iconify from 'src/components/iconify';
import { AddProductView } from 'src/sections/add-product/view';
import { getProducts } from 'src/services/productService';

// ----------------------------------------------------------------------

export default function ProductsView() {
  const [openFilter, setOpenFilter] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [products , setProducts] = useState([]);
  const [sortedProducts, setSortedProducts] = useState([]);

  const [sortOption, setSortOption] = useState('newest');

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleProductAddedSuccess = () => {
    fetchProducts(); // Refresh the product list
    handleCloseModal(); // Close the modal
  };

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

  const fetchProducts = async () => {
    try {
      const response = await getProducts();
      if (response?.data?.data) {
        setProducts(response.data.data);
        setSortedProducts(response.data.data);
      }
    } catch (error) {
      console.error("Failed to fetch products:", error);
    }
  };
  useEffect( ()=>{
    fetchProducts();
    // console.log(response);
  },[]);

  const handleSortChange = (option) => {
    setSortOption(option);
    let sortedList = [...products];
    console.log("sortedList ", sortedList, option);
    switch (option) {
      case 'newest':
        sortedList.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
        break;
      case 'priceAsc':
        sortedList.sort((a, b) => a.price - b.price);
        break;
      case 'priceDesc':
        sortedList.sort((a, b) => b.price - a.price);
        break;
      case 'featured':
        // Implement sorting logic for featured products if needed
        break;
      default:
        break;
    }

    setSortedProducts(sortedList);
  };

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
        <Typography variant="h4">Products</Typography>

        <Button variant="contained" color="inherit" startIcon={<Iconify icon="eva:plus-fill" />} onClick={handleOpenModal} >
          Add Product
        </Button>
      </Stack>
      {/* <Typography variant="h4" sx={{ mb: 5 }}>
        Products
      </Typography> */}

      <Stack
        direction="row"
        alignItems="center"
        flexWrap="wrap-reverse"
        justifyContent="flex-end"
        sx={{ mb: 5 }}
      >
        <Stack direction="row" spacing={1} flexShrink={0} sx={{ my: 1 }}>
         
          <ProductSort  onSortChange={handleSortChange}/>
        </Stack>
      </Stack>

      <Grid container spacing={3}>
        {sortedProducts.map((product) => (
          <Grid key={product.id} xs={12} sm={6} md={3}>
            <ProductCard product={product} />
          </Grid>
        ))}
      </Grid>

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
            maxWidth: 800,
            borderRadius: 2,
          }}
        >
          <Stack direction="row" justifyContent="space-between" alignItems="center" mb={2}>
            <Typography id="add-product-modal" variant="h6" component="h2">
              Add Product
            </Typography>
            <IconButton onClick={handleCloseModal}>
            <Iconify icon="eva:close-outline" />
            </IconButton>
          </Stack>
          <AddProductView onSuccess={handleProductAddedSuccess}/>
        </Box>
      </Modal>
    </Container>
  );
}
