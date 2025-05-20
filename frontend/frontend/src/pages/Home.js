import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Typography, Button, Box, AppBar, Toolbar } from '@mui/material';
import { authService } from '../services/api';

const Home = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await authService.logout();
    navigate('/login');
  };

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Ishema Hotel
          </Typography>
          <Button color="inherit" onClick={handleLogout}>
            Logout
          </Button>
        </Toolbar>
      </AppBar>
      <Container>
        <Box sx={{ mt: 4 }}>
          <Typography variant="h4" component="h1" gutterBottom>
            Welcome to Ishema Hotel
          </Typography>
          <Typography variant="body1">
            This is your dashboard. More features coming soon!
          </Typography>
        </Box>
      </Container>
    </>
  );
};

export default Home; 