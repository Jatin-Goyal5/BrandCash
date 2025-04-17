import ProductFilters from "src/sections/products/product-filters";
import ShopProductSort from "src/sections/products/product-sort";
import Stack from "@mui/material/Stack";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Unstable_Grid2";
import Typography from "@mui/material/Typography";
import { useState } from "react";
import {
  Alert,
  Backdrop,
  Box,
  Button,
  Card,
  CardHeader,
  CircularProgress,
  Divider,
  FormHelperText,
  InputLabel,
  OutlinedInput,
  TextField,
} from "@mui/material";
import { Formik } from "formik";
import UploadFile from "../upload-file";
import * as Yup from "yup"; // Import Yup
import { alpha, useTheme } from "@mui/material/styles";
import { saveProduct } from "src/services/productService";

export default function AddProductView({ onSuccess }) {
  const theme = useTheme();
  const handleSubmit = async (
    values,
    { setSubmitting, setErrors, resetForm }
  ) => {
    try{
      console.log("values", values);
      await saveProduct(values);
      if (onSuccess) onSuccess();

    }catch(ex){
      setErrors({ submit: ex.message });
    }
  };

  const validationSchema = Yup.object().shape({
    title: Yup.string().required("title is required"),
    description: Yup.string().required("product description is required"),
  });

  return (
    // <Container>

    <Grid item xs={12}>
      <Formik
        initialValues={{
          title: "",
          description: "",
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
            <Stack spacing={1} sx={{ color: "inherit" }}>
              <Card>
                <CardHeader
                  title="Details"
                  subheader="Title, short description..."
                />
                <Divider />
                <Stack
                  sx={{
                    p: 3,
                    display: "flex",
                    flexDirection: "column",
                    gap: 1,
                  }}
                >
                  <TextField
                    fullWidth
                    required
                    id="product-title"
                    label="Product title"
                    name="title"
                    variant="outlined"
                    value={values.title}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    error={Boolean(touched.title && errors.title)}
                  />
                  {touched.title && errors.title && (
                    <FormHelperText error id="helper-text-product-title">
                      {errors.title}
                    </FormHelperText>
                  )}
                  <TextField
                    fullWidth
                    required
                    id="product-desc"
                    label="Product description"
                    name="description"
                    variant="outlined"
                    value={values.description}
                    multiline
                    rows={4}
                    error={Boolean(touched.description && errors.description)}
                    onBlur={handleBlur}
                    onChange={handleChange}
                  />
                  {touched.description && errors.description && (
                    <FormHelperText error id="helper-text-product-desc">
                      {errors.description}
                    </FormHelperText>
                  )}

                  {/* <UploadFile></UploadFile> */}
                </Stack>
                <Stack
                  sx={{
                    display: "flex",
                    justifyContent: "flex-end",
                    flexDirection: "row",
                    m: 2,
                  }}
                >
                  <Button
                    size="large"
                    color="inherit"
                    variant="contained"
                    sx={{ borderColor: alpha(theme.palette.grey[500], 0.16) }}
                    onClick={handleSubmit}
                    disabled={isSubmitting}
                  >
                    {" "}
                    Add Product
                  </Button>
                </Stack>
              </Card>
            </Stack>
          </form>
        )}
      </Formik>
    </Grid>
    // </Container>
  );
}
