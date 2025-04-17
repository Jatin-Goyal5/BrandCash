import React, { useContext, useEffect, useState } from "react";
import {
  Card,
  CardContent,
  Typography,
  Stack,
  Button,
  Divider,
  TextField,
  Box,
  InputLabel,
  Select,
  MenuItem,
  Container,
  OutlinedInput,
  FormControl,
  Grid,
  Alert,
  Backdrop,
  CircularProgress,
  FormHelperText,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import { Formik } from "formik";
import { getProducts } from "src/services/productService";
import * as Yup from "yup"; // Import Yup
import { createOrder } from "src/services/orderService";
import ToastContext from "src/context/user/ToastContext";

const OrderView = ({onSuccess}) => {
  const [products, setProducts] = useState([]);
  // const toast
  const { showToast } = useContext(ToastContext);

  const validationSchema = Yup.object().shape({
    productId: Yup.string().required("Product selection is required"),
    quantity: Yup.number()
      .required("Quantity is required")
      .positive("Quantity must be greater than 0"),
    cashbackAmount: Yup.number()
      .required("Cashback amount is required")
      .positive("Amount must be greater than 0"),
    address: Yup.string().required("Address is required"),
    });

  const handleSubmit = async (values, { setSubmitting, setErrors, resetForm }) => {
    console.log("values", values);
    try {
    setSubmitting(true);
    const response =  await createOrder(values);
    showToast('Order placed successfully!', 'success');

    console.log(response);
    if(onSuccess) onSuccess();
  } catch (error) {
      setErrors({ submit: error.message });
    } finally {
      // setSubmitting(false);
    }
  };

  const fetchProducts = async () => {
    try {
      const response = await getProducts();
      if (response?.data?.data) {
        setProducts(response.data.data);
      }
    } catch (error) {
      console.error("Failed to fetch products:", error);
    }
  };
  useEffect(() => {
    fetchProducts();
    // console.log(response);
  }, []);

  return (
    <Container>
      <Grid item xs={12}>
        <Formik
          initialValues={{
            productId: "",
            quantity: 0,
            cashbackAmount: "",
            address: "",
          }}
          onSubmit={handleSubmit}
          validationSchema={validationSchema}
        >
          {({
            errors,
            handleBlur,
            handleChange,
            handleSubmit,
            isSubmitting,
            touched,
            values,
          }) => (
            <form noValidate onSubmit={() => {}}>

{errors.submit && (
              <Grid item xs={12}>
                <Box>
                  <Alert severity="error" variant="outlined">
                    {errors.submit}
                  </Alert>
                </Box>
              </Grid>
            )}
            <Backdrop
              sx={{
                // color: "inherit",
                // backgroundColor:'inherit',
                opacity: 0.7,
                borderRadius: 2,
                zIndex: (theme) => theme.zIndex.drawer + 1,
              }}
              open={isSubmitting}
            >
              <CircularProgress />
            </Backdrop>

              <Card elevation={0} sx={{ borderRadius: 2, padding: 2 }}>
                <CardContent>
                  {/* address section */}
                  <Stack spacing={2}>
                    <Stack
                      direction="row"
                      alignItems="center"
                      justifyContent="space-between"
                    >
                      <Typography variant="h6">To:</Typography>
                    </Stack>
                    <Stack spacing={0.5}>
                      <TextField
                        required
                        id="address"
                        label="Address description"
                        name="address"
                        multiline
                        rows={4}
                        type="text"
                        value={values.address}
                        onBlur={handleBlur}
                        onChange={handleChange}
                        placeholder="Template name"
                        fullWidth
                        error={Boolean(touched.address && errors.address)}
                      />
                      {touched.address && errors.address && (
                        <FormHelperText error id="helper-text-firstname-signup">
                          {errors.address}
                        </FormHelperText>
                      )}
                    </Stack>
                  </Stack>
                  <Divider sx={{ my: 4 }} />

                  {/* Item Details */}
                  <Box>
                    <Typography variant="h6">Details:</Typography>
                    <Stack
                      sx={{
                        display: "flex",
                        flexDirection: "row",
                        gap: 5,
                        mt: 2,
                        justifyContent: "space-between",
                      }}
                    >
                      <Stack
                        sx={{ display: "flex", flexDirection: "row", gap: 5 }}
                      >
                        <FormControl sx={{ minWidth: 220 }} size="small">
                          <InputLabel id="product-label">Product</InputLabel>
                          <Select
                            labelId="product-label"
                            id="product-select"
                            name="productId"
                            value={values.productId}
                            label="Product"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            error={Boolean(touched.productId && errors.productId)}

                          >
                            {products.map((product) => (
                              <MenuItem key={product.id} value={product.id}>
                                {product.name}
                              </MenuItem>
                            ))}
                          </Select>
                          {touched.productId && errors.productId && (
                            <FormHelperText error>
                              {errors.productId}
                            </FormHelperText>
                          )}
                        </FormControl>
                        <Stack>
                        <TextField
                          required
                          id="quantity"
                          label="Quantity"
                          name="quantity"
                          type="number"
                          value={values.quantity}
                          onBlur={handleBlur}
                          onChange={handleChange}
                          placeholder="Quantity"
                          size="small"
                          error={Boolean(touched.quantity && errors.quantity)}
                        />
                        {touched.quantity && errors.quantity && (
                          <FormHelperText
                            error
                            id="helper-text-firstname-signup"
                          >
                            {errors.quantity}
                          </FormHelperText>
                        )}
                        </Stack>

                        <Stack>

                        <TextField
                          required
                          id="cashbackAmount"
                          label="Cashback amount"
                          name="cashbackAmount"
                          type="text"
                          value={values.cashbackAmount}
                          onBlur={handleBlur}
                          onChange={handleChange}
                          placeholder="Amount (e.g., 10)"
                          size="small"
                          error={Boolean(
                            touched.cashbackAmount && errors.cashbackAmount
                          )}
                        />
                        {touched.cashbackAmount && errors.cashbackAmount && (
                          <FormHelperText
                            error
                            id="helper-text-firstname-signup"
                          >
                            {errors.cashbackAmount}
                          </FormHelperText>
                        )}
                        </Stack>
                      </Stack>

                      <TextField
                        label="Total"
                        name="items[0].total"
                        variant="outlined"
                        size="small"
                        disabled
                        InputProps={{
                          startAdornment: <Typography>₹{values.cashbackAmount*values.quantity}</Typography>,
                        }}
                      />
                    </Stack>
                  </Box>

                  <Divider sx={{ my: 4 }} />
                  {/* Summary */}
                  <Stack direction="row" justifyContent="flex-end" gap={6}>
                    <Box>
                      <Typography>Subtotal</Typography>
                      <Typography>Shipping</Typography>
                      <Typography>Discount</Typography>
                      <Typography>Taxes</Typography>
                      <Typography>Total:</Typography>
                    </Box>
                    <Box>
                      <Typography>₹ 0</Typography>
                      <Typography>-</Typography>
                      <Typography>-</Typography>
                      <Typography>-</Typography>
                      <Typography>-</Typography>
                    </Box>
                  </Stack>
                  <Divider sx={{ my: 2 }} />

                  {/* Actions */}
                  <Stack direction="row" justifyContent="flex-end" mt={2}>
                    <Button
                      variant="contained"
                      size="large"
                      color="inherit"
                      type="submit"
                    onClick={handleSubmit}

                      disabled={isSubmitting}
                    >
                      Order
                    </Button>
                  </Stack>
                </CardContent>
              </Card>
            </form>
          )}
        </Formik>
      </Grid>
    </Container>
  );
};

export default OrderView;
