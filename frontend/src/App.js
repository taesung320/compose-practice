import React, { Fragment, useEffect, useState } from "react";

import "./App.css";

import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import axios from "axios";

function FormDialog({onSubmit}) {
  const [open, setOpen] = React.useState(false);
  const [title, setTitle] = React.useState('');
  const [description, setDescription] = React.useState('');
  const handleTitle = (event) => {
    setTitle(event.target.value);
  };
  const handleDescription = (event) => {
    setDescription(event.target.value);
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        create product
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>create product</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="title"
            label="title"
            type="text"
            fullWidth
            variant="standard"
            value={title}
            onChange={handleTitle}
          />
          <TextField
            autoFocus
            margin="dense"
            id="description"
            label="description"
            type="text"
            fullWidth
            variant="standard"
            value={description}
            onChange={handleDescription}
          />
          
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={()=>{
            handleClose();
            onSubmit({title: title,description: description});
          }}>submit</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

function App() {
  const columns=[
    {
      name:"id"
    },
    {
      name:"title"
    },
    {
      name:"description"
    },
    {
      name:"delete"
    }
  ];
  const [products,setProducts]=useState([
      {
        id:1,
        title:"a",
        description:"a-a"
      },
      {
        id:2,
        title:"b",
        description:"a-a"
      },
      {
        id:3,
        title:"c",
        description:"a-a"
      },
      {
        id:4,
        title:"d",
        description:"a-a"
      },
  ]);
  function axiosGet(postfix){
    return axios.get(`${process.env.PUBLIC_URL}/api${postfix}`);
  }
  function axiosPost(postfix,body){
    return axios.post(`${process.env.PUBLIC_URL}/api${postfix}`,body);
  }
  function axiosDelete(postfix,pathParam){
    return axios.delete(`${process.env.PUBLIC_URL}/api${postfix}/${pathParam}`);
  }
  function fetchData(){
    axiosGet("/products").then((res)=>{
      setProducts(res.data.products);
    }).catch((err)=>{
      console.log(err);
    })
  }

  function createData(data){
    axiosPost("/products",data).then((res)=>{
      console.log(res);
      fetchData();
    }).catch((err)=>{
      console.log(err);
    })
  }

  function deleteRow(data){
    axiosDelete("/products",data.id).then((res)=>{
      console.log(res);
      fetchData();
    }).catch((err)=>{
      console.log(err);
    })
  }

  useEffect(()=>{
    console.log("a");
    fetchData();
  },[])

  function renderRows(rows){
    return rows.map((row) => (
      <StyledTableRow key={row.id}>
        <StyledTableCell component="th" scope="row">
          {row.id}
        </StyledTableCell>
        <StyledTableCell align="center">{row.title}</StyledTableCell>
        <StyledTableCell align="center">{row.description}</StyledTableCell>
        <StyledTableCell align="center">
          <Button variant="outlined" onClick={()=>{deleteRow(row)}}>Delete</Button> 
        </StyledTableCell>
        
      </StyledTableRow>
    ))
  }
  function renderColumns(columns){
    return columns.map((item)=>(
      <StyledTableCell>{item.title}</StyledTableCell>
    ))
  }

  return (<>
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 500 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            {renderColumns(columns)}
          </TableRow>
        </TableHead>
        <TableBody>
            {renderRows(products)}
        </TableBody>
      </Table>
    </TableContainer>
    <FormDialog onSubmit={createData}/>
    </>
  );
}





export default App;
