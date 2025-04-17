import { useContext, useState } from "react";

import Box from "@mui/material/Box";
import Link from "@mui/material/Link";
import Card from "@mui/material/Card";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import LoadingButton from "@mui/lab/LoadingButton";
import { alpha, useTheme } from "@mui/material/styles";
import InputAdornment from "@mui/material/InputAdornment";

import { useRouter } from "src/routes/hooks";

import { bgGradient } from "src/theme/css";

import Logo from "src/components/logo";
import Iconify from "src/components/iconify";
import { Formik } from "formik";
import * as Yup from "yup"; // Import Yup
import { FormHelperText, Grid } from "@mui/material";
import userContext from "src/context/user/userContext";

// ----------------------------------------------------------------------

export default function LoginView() {
  const theme = useTheme();
  const router = useRouter();
  
  const { login } = useContext(userContext);
  const [showPassword, setShowPassword] = useState(false);

  const handleClick = () => {
    router.push("/login");
  };

  const validationSchema = Yup.object().shape({
    email: Yup.string()
      .email("Invalid email format")
      .required("Email is required"),
    password: Yup.string().required("Password is required"),
  });

  const handleSubmit = async (values, { setSubmitting, setErrors, resetForm }) => {
    console.log("values", values);
    try {
    const response =  await login(values);
    if(response != null && response != undefined && response.status == 200){

      resetForm();
      router.push('/'); 
      // router.push('/login'); // Or any other route you want

    }
    console.log(response);
    } catch (error) {
      setErrors({ submit: error.message });
    } finally {
      setSubmitting(false);
    }
  };


  const renderForm = (
    <>
      <Formik
        initialValues={{
          email: "",
          password: "",
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
          <form noValidate onSubmit={handleSubmit}>
    <Grid item xs={12} sx={{ gap:2, display:'flex', flexDirection:'column',}}>
    <Stack >
                <TextField
                  name="email"
                  label="Email address"
                  id="email-login"
                  type="email"
                  value={values.email}
                  onBlur={handleBlur}
                  onChange={handleChange}
                  placeholder="demo@companyName.com"
                  fullWidth
                  error={Boolean(touched.email && errors.email)}
                />
              {touched.email && errors.email && (
                <FormHelperText error id="helper-text-email-signup">
                  {errors.email}
                </FormHelperText>
              )}
              </Stack>
              <Stack>

              <TextField
                name="password"
                label="Password"
                fullWidth
                error={Boolean(touched.password && errors.password)}
                id="password-signup"
                type={showPassword ? 'text' : 'password'}
                value={values.password}
                placeholder="******"
                onChange={(e) => {
                  handleChange(e);
                  // changePassword(e.target.value);
                }}
               
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={() => setShowPassword(!showPassword)}
                        edge="end"
                      >
                        <Iconify
                          icon={
                            showPassword ? "eva:eye-fill" : "eva:eye-off-fill"
                          }
                        />
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              {touched.password && errors.password && (
                  <FormHelperText error id="helper-text-password-signup">
                    {errors.password}
                  </FormHelperText>
                )}
              </Stack>
            </Grid>

            <Stack
              direction="row"
              alignItems="center"
              justifyContent="flex-end"
              sx={{ my: 3 }}
            >
              <Link variant="subtitle2" underline="hover">
                Forgot password?
              </Link>
            </Stack>

            <LoadingButton
              fullWidth
              size="large"
              type="submit"
              variant="contained"
              color="inherit"
              onClick={handleClick}
            >
              Login
            </LoadingButton>
          </form>
        )}
      </Formik>
    </>
  );

  return (
    <Box
      sx={{
        ...bgGradient({
          color: alpha(theme.palette.background.default, 0.9),
          imgUrl: "/assets/background/overlay_4.jpg",
        }),
        height: 1,
      }}
    >
      <Logo
        sx={{
          position: "fixed",
          top: { xs: 16, md: 24 },
          left: { xs: 16, md: 24 },
        }}
      />

      <Stack alignItems="center" justifyContent="center" sx={{ height: 1 }}>
        <Card
          sx={{
            p: 5,
            width: 1,
            maxWidth: 420,
          }}
        >
          <Typography variant="h4">Sign in to BrandBacks</Typography>

          <Typography variant="body2" sx={{ mt: 1, mb: 3 }}>
            Don’t have an account?
            <Link variant="subtitle2" sx={{ ml: 0.5 }}>
              Get started
            </Link>
          </Typography>

          {renderForm}
        </Card>
      </Stack>
    </Box>
  );
}
