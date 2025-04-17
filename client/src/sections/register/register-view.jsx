import { useContext, useState } from 'react';

import Box from '@mui/material/Box';
// import Link from '@mui/material/Link';
import Card from '@mui/material/Card';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import LoadingButton from '@mui/lab/LoadingButton';
import { alpha, useTheme } from '@mui/material/styles';
import InputAdornment from '@mui/material/InputAdornment';
import { Link } from 'react-router-dom';
import { useRouter } from 'src/routes/hooks';

import { bgGradient } from 'src/theme/css';

import Logo from 'src/components/logo';
import Iconify from 'src/components/iconify';
import { Alert, FormControl, FormHelperText, Grid, InputLabel, OutlinedInput, Snackbar } from '@mui/material';
import { Formik } from 'formik';
import userContext from 'src/context/user/userContext';
import * as Yup from 'yup'; // Import Yup
import MuiAlert from '@mui/material/Alert';

// ----------------------------------------------------------------------

export default function RegisterView() {
  const theme = useTheme();

  const router = useRouter();

  const [showPassword, setShowPassword] = useState(false);
  const [level, setLevel] = useState();
  const { register } = useContext(userContext);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  
  const handleClick = () => {
    router.push('/dashboard');
  };

  const handleSubmit = async (values, { setSubmitting, setErrors, resetForm }) => {
    console.log("values", values);
    try {
    const response =  await register(values);
    if(response != null && response != undefined && response.status == 200){
      setOpenSnackbar(true);

      resetForm();
      setTimeout(() => router.push('/login'), 3000); 
      // router.push('/login'); // Or any other route you want

    }
    console.log(response);
    } catch (error) {
      setErrors({ submit: error.message });
    } finally {
      setSubmitting(false);
    }
  };


const handleSnackbarClose = (event, reason) => {
  if (reason === 'clickaway') {
    return;
  }
  setOpenSnackbar(false);
};


  const validationSchema = Yup.object().shape({
    firstName: Yup.string().required('First Name is required'),
    lastName: Yup.string().required('Last Name is required'),
    email: Yup.string().email('Invalid email format').required('Email is required'),
    companyNameName: Yup.string(),
    password: Yup.string()
      .min(6, 'Password must be at least 6 characters')
      .required('Password is required'),
  });

  const renderForm = (
    <Grid item xs={12}>
      <Formik
        initialValues={{
          firstName: '',
          lastName: '',
          email: '',
          companyName: '',
          password: '',
        }}
        onSubmit={handleSubmit}
        validationSchema={validationSchema}

      >
        {({ errors, handleBlur, handleChange, handleSubmit, isSubmitting, touched, values }) => (
          <form noValidate onSubmit={handleSubmit}>
            <Grid container spacing={3}>
            {errors.submit && (
              <Grid item xs={12}>
                 <Box >
                  <Alert severity="error" variant="outlined">
                    {errors.submit}
                  </Alert>
                </Box>
              </Grid>
            )}
              <Grid item xs={12} md={6}>
                <Stack spacing={1}>
                  <InputLabel htmlFor="firstName-signup">First Name*</InputLabel>
                  <OutlinedInput
                    id="firstName-login"
                    type="firstName"
                    value={values.firstName}
                    name="firstName"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    placeholder="John"
                    fullWidth
                    error={Boolean(touched.firstName && errors.firstName)}
                  />
                </Stack>
                {touched.firstName && errors.firstName && (
                  <FormHelperText error id="helper-text-firstName-signup">
                    {errors.firstName}
                  </FormHelperText>
                )}
              </Grid>
              <Grid item xs={12} md={6}>
                <Stack spacing={1}>
                  <InputLabel htmlFor="lastName-signup">Last Name*</InputLabel>
                  <OutlinedInput
                    fullWidth
                    error={Boolean(touched.lastName && errors.lastName)}
                    id="lastName-signup"
                    type="lastName"
                    value={values.lastName}
                    name="lastName"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    placeholder="Doe"
                    inputProps={{}}
                  />
                </Stack>
                {touched.lastName && errors.lastName && (
                  <FormHelperText error id="helper-text-lastName-signup">
                    {errors.lastName}
                  </FormHelperText>
                )}
              </Grid>
              <Grid item xs={12}>
                <Stack spacing={1}>
                  <InputLabel htmlFor="companyName-signup">companyName</InputLabel>
                  <OutlinedInput
                    fullWidth
                    error={Boolean(touched.companyName && errors.companyName)}
                    id="companyName-signup"
                    value={values.companyName}
                    name="companyName"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    placeholder="Demo Inc."
                    inputProps={{}}
                  />
                </Stack>
                {touched.companyName && errors.companyName && (
                  <FormHelperText error id="helper-text-companyName-signup">
                    {errors.companyName}
                  </FormHelperText>
                )}
              </Grid>
              <Grid item xs={12}>
                <Stack spacing={1}>
                  <InputLabel htmlFor="email-signup">Email Address*</InputLabel>
                  <OutlinedInput
                    fullWidth
                    error={Boolean(touched.email && errors.email)}
                    id="email-login"
                    type="email"
                    value={values.email}
                    name="email"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    placeholder="demo@companyName.com"
                    inputProps={{}}
                  />
                </Stack>
                {touched.email && errors.email && (
                  <FormHelperText error id="helper-text-email-signup">
                    {errors.email}
                  </FormHelperText>
                )}
              </Grid>
              <Grid item xs={12}>
                <Stack spacing={1}>
                  <InputLabel htmlFor="password-signup">Password</InputLabel>
                  <OutlinedInput
                    fullWidth
                    error={Boolean(touched.password && errors.password)}
                    id="password-signup"
                    type={showPassword ? 'text' : 'password'}
                    value={values.password}
                    name="password"
                    onBlur={handleBlur}
                    onChange={(e) => {
                      handleChange(e);
                      // changePassword(e.target.value);
                    }}
                    
                    endAdornment={
                      <InputAdornment position="end">
                        <IconButton
                          aria-label="toggle password visibility"
                          onClick={() => setShowPassword(!showPassword)}
                          onMouseDown={() => {}}
                          edge="end"
                          color="secondary"
                        >
                          <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                        </IconButton>
                      </InputAdornment>
                    }
                    placeholder="******"
                    
                  />
                </Stack>
                {touched.password && errors.password && (
                  <FormHelperText error id="helper-text-password-signup">
                    {errors.password}
                  </FormHelperText>
                )}

              </Grid>
            </Grid>

            <Grid item xs={12} sx={{ mt: 2 }}>
              <Typography variant="body2">
                By Signing up, you agree to our &nbsp;
                <Link variant="subtitle2" to="#">
                  Terms of Service
                </Link>
                &nbsp; and &nbsp;
                <Link variant="subtitle2" to="#">
                  Privacy Policy
                </Link>
              </Typography>
            </Grid>
            
            <Grid item xs={12} sx={{ mt: 2 }}>
              <Button
                fullWidth
                size="large"
                color="inherit"
                variant="contained"
                type='submit'
                sx={{ borderColor: alpha(theme.palette.grey[500], 0.16) }}
              >
                {' '}
                Create an account
              </Button>
            </Grid>
          </form>
        )}
      </Formik>
    </Grid>
  );

  return (
    <>
    <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleSnackbarClose} 
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }} 
      sx={{
        width: '100%',
        maxWidth: '500px', // Maximum width
        margin: '0 auto',  // Center-align for wider screens
      }}
    
    >
  <MuiAlert onClose={handleSnackbarClose} severity="success" sx={{ width: '100%' }}>
    Registration successful!
  </MuiAlert>
</Snackbar>
    <Box
      sx={{
        ...bgGradient({
          color: alpha(theme.palette.background.default, 0.9),
          imgUrl: '/assets/background/overlay_4.jpg',
        }),
        height: 1,
      }}
    >
      <Logo
        sx={{
          position: 'fixed',
          top: { xs: 16, md: 24 },
          left: { xs: 16, md: 24 },
        }}
      />

      <Stack alignItems="center" justifyContent="center" sx={{ height: 1 }}>
        <Card
          sx={{
            p: 5,
            width: 1,
            maxWidth: 530,
          }}
        >
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Stack
                direction="row"
                justifyContent="space-between"
                alignItems="baseline"
                sx={{ mb: { xs: -0.5, sm: 0.5 } }}
              >
                <Typography variant="h3">Sign up</Typography>
                <Typography
                  component={Link}
                  to="/login"
                  variant="body1"
                  sx={{ textDecoration: 'none' }}
                  color="primary"
                >
                  Already have an account?
                </Typography>
              </Stack>
            </Grid>

            {renderForm}
          </Grid>
        </Card>
      </Stack>
    </Box>
    </>
  );
}
