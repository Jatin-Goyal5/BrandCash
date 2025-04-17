
import Container from '@mui/material/Container';
import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import Iconify from 'src/components/iconify';

import AppWebsiteVisits from '../app-website-visits';
import AppWidgetSummary from '../app-widget-summary';
import { Box, Button, Card, CardHeader, Stack } from '@mui/material';
import UserTablePage from 'src/sections/user/view/user-view-table';

// ----------------------------------------------------------------------

export default function AppView() {
  return (
    <Container maxWidth="l">
      <Typography variant="h4" sx={{ mb: 5 }}>
        Hi, Welcome back 👋
      </Typography>

      <Grid container spacing={2}>
        <Grid item xs={12} md={6} lg={6} sx={{ minHeight: 200 }}>
        <Card sx={{ p: 3, borderRadius: 3, boxShadow: 3 }}>
          <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
            <Box>
              <Typography variant="h6" color="primary" fontWeight="bold">
                Today’s Sales
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Sales Summary
              </Typography>
            </Box>
            <Button variant="outlined" >
              Export
            </Button>
          </Box>
          <Box display="flex"  alignItems="center">

                 <Grid item  xs={12} sm={6} md={3}>
                  <AppWidgetSummary
                    title="Weekly Sales"
                    total={714000}
                    color="success"
                    icon={<img alt="icon" src="/assets/icons/glass/ic_glass_bag.png" />}
                  />
                </Grid>

                <Grid item  xs={12} sm={6} md={3}>
                  <AppWidgetSummary
                    title="New Users"
                    total={1352831}
                    color="info"
                    icon={<img alt="icon" src="/assets/icons/glass/ic_glass_users.png" />}
                  />
                </Grid>

                <Grid  xs={12} sm={6} md={3}>
                  <AppWidgetSummary
                    title="Item Orders"
                    total={1723315}
                    color="warning"
                    icon={<img alt="icon" src="/assets/icons/glass/ic_glass_buy.png" />}
                  />
              </Grid>
          </Box>
          </Card>
        </Grid>

        <Grid item xs={12} md={6} lg={6} sx={{ minHeight: 200 }}>
          <AppWebsiteVisits
            title="Website Visits"
            subheader="(+43%) than last year"
            chart={{
              labels: [
                '01/01/2003',
                '02/01/2003',
                '03/01/2003',
                '04/01/2003',
                '05/01/2003',
                '06/01/2003',
                '07/01/2003',
                '08/01/2003',
                '09/01/2003',
                '10/01/2003',
                '11/01/2003',
              ],
              series: [
                {
                  name: 'Product A',
                  type: 'column',
                  fill: 'solid',
                  data: [23, 11, 22, 27, 13, 22, 37, 21, 44, 22, 30],
                },
                {
                  name: 'Product B',
                  type: 'area',
                  fill: 'gradient',
                  data: [44, 55, 41, 67, 22, 43, 21, 41, 56, 27, 43],
                },
                {
                  name: 'Product C',
                  type: 'line',
                  fill: 'solid',
                  data: [30, 25, 36, 30, 45, 35, 64, 52, 59, 36, 39],
                },
              ],
            }}
            style={{ height: '100%' }}
          />
        </Grid>
      </Grid>

      <Container maxWidth="l" sx={{ mt: 5 }}>
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4">QR Claim History</Typography>

        
        </Stack>

        <UserTablePage />
      </Container>
    </Container>
  );
}
