import React, { useEffect, useState } from "react";
import logo from "./logo.svg";
import "./App.css";
import CRUDTable,
{
  Fields,
  Field,
  CreateForm,
  UpdateForm,
  DeleteForm,
} from 'react-crud-table';
import axios from 'axios';

// Component's Base CSS
import './index.css';

const DescriptionRenderer = ({ field }) => <textarea {...field} />;

let tasks = [
  {
    id: 1,
    title: 'Create an example',
    description: 'Create an example of how to use the component',
  },
  {
    id: 2,
    title: 'Improve',
    description: 'Improve the component!',
  },
];

const SORTERS = {
  NUMBER_ASCENDING: mapper => (a, b) => mapper(a) - mapper(b),
  NUMBER_DESCENDING: mapper => (a, b) => mapper(b) - mapper(a),
  STRING_ASCENDING: mapper => (a, b) => mapper(a).localeCompare(mapper(b)),
  STRING_DESCENDING: mapper => (a, b) => mapper(b).localeCompare(mapper(a)),
};

const getSorter = (data) => {
  const mapper = x => x[data.field];
  let sorter = SORTERS.STRING_ASCENDING(mapper);

  if (data.field === 'id') {
    sorter = data.direction === 'ascending' ?
      SORTERS.NUMBER_ASCENDING(mapper) : SORTERS.NUMBER_DESCENDING(mapper);
  } else {
    sorter = data.direction === 'ascending' ?
      SORTERS.STRING_ASCENDING(mapper) : SORTERS.STRING_DESCENDING(mapper);
  }

  return sorter;
};


let count = tasks.length;
const backendUrl="http://backend:8080";
function axiosGet(postfix){
  return axios.get(`${backendUrl}${postfix}`).resolve()
}
function axiosPost(postfix,body){
  return axios.post(`${backendUrl}${postfix}`,body)
}
//여기서 api 호출하면됨
const service = {
  fetchItems: (payload) => {
    let result = Array.from(tasks);
    result = result.sort(getSorter(payload.sort));
    return Promise.resolve(result);
  },
  create: (task) => {
    count += 1;
    tasks.push({
      ...task,
      id: count,
    });
    return Promise.resolve(task);
  },
  update: (data) => {
    const task = tasks.find(t => t.id === data.id);
    task.title = data.title;
    task.description = data.description;
    return Promise.resolve(task);
  },
  delete: (data) => {
    const task = tasks.find(t => t.id === data.id);
    tasks = tasks.filter(t => t.id !== task.id);
    return Promise.resolve(task);
  },
};

const styles = {
  container: { margin: 'auto', width: 'fit-content' },
};

function App() {
  const [title,setTitle]=useState("aaa");
  useEffect(()=>{

  })
  function onSearch(){
    console.log(title);
    axios.get("/").then((response)=>{
      console.log("succ");
    }).catch((errors)=>{
      console.log("fail",errors);
    })
  } 
  return (
    <div style={styles.container}>
      <CRUDTable
        caption="Tasks"
        update="false"
        fetchItems={payload => service.fetchItems(payload)}
      >
        <Fields>
          <Field
            name="id"
            label="Id"
            hideInCreateForm
            readOnly
          />
          <Field
            name="title"
            label="Title"
            placeholder="Title"
          />
          <Field
            name="description"
            label="Description"
            render={DescriptionRenderer}
          />
        </Fields>
        <CreateForm
          title="Task Creation"
          message="Create a new task!"
          trigger="Create Task"
          onSubmit={task => service.create(task)}
          submitText="Create"
          validate={(values) => {
            const errors = {};
            if (!values.title) {
              errors.title = 'Please, provide task\'s title';
            }

            if (!values.description) {
              errors.description = 'Please, provide task\'s description';
            }

            return errors;
          }}
        />

        <DeleteForm
          title="Task Delete Process"
          message="Are you sure you want to delete the task?"
          trigger="Delete"
          onSubmit={task => service.delete(task)}
          submitText="Delete"
          validate={(values) => {
            const errors = {};
            if (!values.id) {
              errors.id = 'Please, provide id';
            }
            return errors;
          }}
        />
      </CRUDTable>
      <input type="text" placeholder="search title" value={title} onChange={setTitle} />
      <button onClick={onSearch}>검색</button>
    </div>
  );
}




export default App;
