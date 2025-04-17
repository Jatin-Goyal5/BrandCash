import { Card, CardHeader, Container, Divider, FormHelperText, Grid, InputLabel, OutlinedInput, Stack, Button, Checkbox, FormControlLabel } from "@mui/material";
import { Formik } from "formik";
import * as Yup from "yup";

export default function ConfigureAccountForm() {
    const validationSchema = Yup.object().shape({
        bank_codes: Yup.string().required( "bank code is required"),
        name: Yup.string().required("Account name is required"),
        pan: Yup.string().matches(/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/, "Invalid PAN format").required("PAN is required"),
        email: Yup.string().email("Invalid email format"),
        mobile: Yup.string().matches(/^[0-9]{10}$/, "Invalid mobile number").required("Mobile number is required"),
        // customer_id: Yup.string().required("Customer ID is required"),
    });

    const handleSubmit = (values) => {
        console.log("Form Values:", values);
    };

    return (
        <Container>
            <Grid item xs={12}>
                <Formik
                    initialValues={{
                        bank_codes: "",
                        name: "",
                        pan: "",
                        email: "",
                        mobile: "",
                        kyc_verified: 1,
                        kyc_check_decentro: 0,
                        customer_id: "",
                        virtual_account_balance_settlement: "enabled",
                        // generate_static_qr: 1,
                    }}
                    onSubmit={handleSubmit}
                    validationSchema={validationSchema}
                >
                    {({
                        errors,
                        handleBlur,
                        handleChange,
                        handleSubmit,
                        touched,
                        values,
                    }) => (
                        <form noValidate onSubmit={handleSubmit}>
                            <Stack spacing={2}>
                                <Card elevation={0}>
                                    <CardHeader
                                        title="Configure Account"
                                        subheader="Provide account details below"
                                    />
                                    <Divider />
                                    <Stack sx={{ p: 3, display: "flex", gap: 3 }}>
                                        <Grid container spacing={2}>
                                            <Grid item xs={12} md={6}>
                                                <Stack spacing={1}>
                                                    <InputLabel htmlFor="name">Account Name*</InputLabel>
                                                    <OutlinedInput
                                                        id="name"
                                                        type="text"
                                                        value={values.name}
                                                        name="name"
                                                        onBlur={handleBlur}
                                                        onChange={handleChange}
                                                        placeholder="Account Name"
                                                        fullWidth
                                                        error={Boolean(touched.name && errors.name)}
                                                    />
                                                    {touched.name && errors.name && (
                                                        <FormHelperText error>{errors.name}</FormHelperText>
                                                    )}
                                                </Stack>
                                            </Grid>
                                            
                                            <Grid item xs={12} md={6}>
                                                <Stack spacing={1}>
                                                    <InputLabel htmlFor="pan">PAN*</InputLabel>
                                                    <OutlinedInput
                                                        id="pan"
                                                        type="text"
                                                        value={values.pan}
                                                        name="pan"
                                                        onBlur={handleBlur}
                                                        onChange={handleChange}
                                                        placeholder="PAN Number"
                                                        fullWidth
                                                        error={Boolean(touched.pan && errors.pan)}
                                                    />
                                                    {touched.pan && errors.pan && (
                                                        <FormHelperText error>{errors.pan}</FormHelperText>
                                                    )}
                                                </Stack>
                                            </Grid>
                                            
                                            {/* <Grid container spacing={2}> */}
                                            <Grid item xs={12} md={6}>
                                                <Stack spacing={1}>
                                                    <InputLabel htmlFor="name"> Bank Code*</InputLabel>
                                                    <OutlinedInput
                                                        id="bank_codes"
                                                        type="text"
                                                        value={values.bank_codes}
                                                        name="bank_codes"
                                                        onBlur={handleBlur}
                                                        onChange={handleChange}
                                                        placeholder="Account Name"
                                                        fullWidth
                                                        error={Boolean(touched.bank_codes && errors.bank_codes)}
                                                    />
                                                    {touched.bank_codes && errors.bank_codes && (
                                                        <FormHelperText error>{errors.bank_codes}</FormHelperText>
                                                    )}
                                                </Stack>
                                            </Grid>
                                     {/* </Grid> */}


                                         
                                            <Grid item xs={12} md={6}>
                                                <Stack spacing={1}>
                                                    <InputLabel htmlFor="mobile">Mobile*</InputLabel>
                                                    <OutlinedInput
                                                        id="mobile"
                                                        type="text"
                                                        value={values.mobile}
                                                        name="mobile"
                                                        onBlur={handleBlur}
                                                        onChange={handleChange}
                                                        placeholder="Mobile Number"
                                                        fullWidth
                                                        error={Boolean(touched.mobile && errors.mobile)}
                                                    />
                                                    {touched.mobile && errors.mobile && (
                                                        <FormHelperText error>{errors.mobile}</FormHelperText>
                                                    )}
                                                </Stack>
                                            </Grid>

                                            <Grid item xs={12} md={6}>
                                                <Stack spacing={1}>
                                                    <InputLabel htmlFor="email">Email</InputLabel>
                                                    <OutlinedInput
                                                        id="email"
                                                        type="email"
                                                        value={values.email}
                                                        name="email"
                                                        onBlur={handleBlur}
                                                        onChange={handleChange}
                                                        placeholder="Email Address"
                                                        fullWidth
                                                        error={Boolean(touched.email && errors.email)}
                                                    />
                                                    {touched.email && errors.email && (
                                                        <FormHelperText error>{errors.email}</FormHelperText>
                                                    )}
                                                </Stack>
                                            </Grid>

                                           
                                        </Grid>
                                        {/* <FormControlLabel
                                            control={<Checkbox checked={values.generate_static_qr === 1} />}
                                            label="Generate Static QR"
                                        /> */}
                                        <Button type="submit" variant="contained" color="primary">
                                            Submit
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
