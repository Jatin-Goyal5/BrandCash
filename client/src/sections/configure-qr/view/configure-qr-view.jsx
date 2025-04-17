
import Stack from "@mui/material/Stack";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Unstable_Grid2";
import Typography from "@mui/material/Typography";
import { useContext, useState } from "react";
import * as Yup from "yup"; // Import Yup
import { QRCodeCanvas } from "qrcode.react"; // Import QR Code library

import {
  Box,
  Button,
  Card,
  CardHeader,
  Divider,
  FormHelperText,
  InputLabel,
  OutlinedInput,
  TextField,
} from "@mui/material";
import { Formik } from "formik";

import { alpha, useTheme } from "@mui/material/styles";
import { saveConfig } from "src/services/QrConfigService";
import ToastContext from "src/context/user/ToastContext";

export default function ConfigureQrView() {
  const theme = useTheme();
  const { showToast } = useContext(ToastContext);

  const validationSchema = Yup.object().shape({
    templateName: Yup.string().required("Template name is required"),
    length: Yup.number()
      .required("Length is required")
      .positive("Must be a positive number"),
      height: Yup.number()
      .required("height is required")
      .positive("Must be a positive number"),
    qrSize: Yup.number()
      .required("QR size is required")
      .positive("Must be a positive number"),
  });

  const handleSubmit = async (
    values,
    { setSubmitting, setErrors, resetForm }
  ) => {
    try {
      console.log("values", values);
     const response  =await saveConfig(values);
      if(response.status == 200){

        showToast(response.data.message, 'success');
      }

      // await saveProduct(values);
      // if (onSuccess) onSuccess();
    } catch (ex) {
      showToast(ex.message, 'success');

      setErrors({ submit: ex.message });
    }
  };

  const [qrData, setQrData] = useState({
    templateName: "",
    length: 60,
    height: 60,
    qrSize: 50,
  });
  return (
    <Container>
      <Grid item xs={12}>
        <Formik
          initialValues={{
            templateName: "",
            length: 100,
            height: 100,
            qrSize: 70,
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
              <Stack spacing={2}>
                <Card elevation={0}>
                  <CardHeader
                    title="Details"
                    subheader="Template, card size, qr size..."
                  />
                  <Divider />
                  <Stack sx={{ p: 3, display: "flex", gap: 3 }}>
                    <Grid item xs={12} md={6}>
                      <Stack spacing={1}>
                        <InputLabel htmlFor="Template-name">
                          Template Name*
                        </InputLabel>
                        <OutlinedInput
                          id="Template-name"
                          type="text"
                          value={values.templateName}
                          name="templateName"
                          onBlur={handleBlur}
                          onChange={handleChange}
                          placeholder="Template name"
                          fullWidth
                          error={Boolean(
                            touched.templateName && errors.templateName
                          )}
                        />
                      </Stack>
                      {touched.templateName && errors.templateName && (
                        <FormHelperText error id="helper-text-firstname-signup">
                          {errors.templateName}
                        </FormHelperText>
                      )}
                    </Grid>
                    <Stack
                      sx={{
                        display: "flex",
                        flexDirection: "row",
                        gap: 2,
                        width: "100%",
                      }}
                    >
                      <Grid item sx={{ width: "100%" }}>
                        <Stack spacing={1}>
                          <InputLabel htmlFor="qr-length">Length*</InputLabel>
                          <OutlinedInput
                            id="qr-length"
                            type="number"
                            value={values.length}
                            name="length"
                            onBlur={handleBlur}
                            onChange={handleChange}
                            placeholder="Size (e.g., 256)"
                            fullWidth
                            error={Boolean(touched.length && errors.length)}
                          />
                        </Stack>
                        {touched.length && errors.length && (
                          <FormHelperText error id="helper-text-qr-length">
                            {errors.length}
                          </FormHelperText>
                        )}
                      </Grid>
                      <Grid item sx={{ width: "100%" }}>
                        <Stack spacing={1}>
                          <InputLabel htmlFor="qr-height">Height*</InputLabel>
                          <OutlinedInput
                            id="qr-height"
                            type="number"
                            value={values.height}
                            name="height"
                            onBlur={handleBlur}
                            onChange={handleChange}
                            placeholder="Size (e.g., 256)"
                            fullWidth
                            error={Boolean(touched.height && errors.height)}
                          />
                        </Stack>
                        {touched.height && errors.height && (
                          <FormHelperText error id="helper-text-qr-width">
                            {errors.height}
                          </FormHelperText>
                        )}
                      </Grid>
                    </Stack>
                    <Grid item xs={6} md={6}>
                      <Stack spacing={1}>
                        <InputLabel htmlFor="Qr-size">
                          QR Code Size (px)*
                        </InputLabel>
                        <OutlinedInput
                          id="qr-size"
                          type="number"
                          value={values.qrSize}
                          name="qrSize"
                          onBlur={handleBlur}
                          onChange={handleChange}
                          placeholder="Size (e.g., 256)"
                          fullWidth
                          error={Boolean(touched.qrSize && errors.qrSize)}
                        />
                      </Stack>
                      {touched.qrSize && errors.qrSize && (
                        <FormHelperText error id="helper-text-firstname-signup">
                          {errors.qrSize}
                        </FormHelperText>
                      )}
                    </Grid>
                  <Grid item xs={6} md={6}>

                  <Stack spacing={1}>
                  <Typography variant="h6">QR Code Preview:</Typography>
                    <Box
                      sx={{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        width: `${values.length || 256}px`, // Set width from form value
                        height: `${values.height || 256}px`, // Set height from form value
                        border: "1px solid #ccc", // Optional: Add a border for better visualization
                        borderRadius: "8px", // Optional: Add rounded corners
                        p: 2, // Optional: Add padding around the QR code
                      }}
                    >
                      {" "}
                      <QRCodeCanvas
                        value={qrData.templateName || "Preview"}
                        size={Number(values.qrSize) || 128}
                      />
                    </Box>
                  </Stack>
</Grid>
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
                      disabled={isSubmitting}
                      onClick={handleSubmit}
                      sx={{ borderColor: alpha(theme.palette.grey[500], 0.16) }}
                    >
                      {" "}
                      Save changes
                    </Button>
                  </Stack>
                </Card>
              </Stack>
            </form>
          )}
        </Formik>
      </Grid>
    </Container>
  );
}
