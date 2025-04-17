import React from 'react';
import { Box, Button, Typography } from '@mui/material';
import SvgColor from 'src/components/svg-color';
import UploadIcon from '../add-product/upload-icon';


export  default function UploadFile(){
        const handleFileChange = (event) => {
          const files = event.target.files;
          console.log(files); // Handle file uploads here
        };
      
    return (
        <Box
        sx={{
          border: '2px dashed #1976d2',
          borderRadius: '8px',
          padding: '20px',
          textAlign: 'center',
          cursor: 'pointer',
        }}
        onClick={() => document.getElementById('file-upload').click()}
      >
        <input
          type="file"
          id="file-upload"
          accept="image/*"
          multiple
          onChange={handleFileChange}
          style={{ display: 'none' }}
        />
  <Box
      sx={{
        display: 'inline-block',
      }}
    >
        <UploadIcon  width={200} height={150}/>
    </Box>

        <Typography variant="h6" sx={{ marginTop: '10px' }}>
          Drop or select file
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Drop files here or click to browse through your machine.
        </Typography>
      </Box>
    );

}