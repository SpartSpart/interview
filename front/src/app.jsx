import {BrowserRouter, Routes, Route} from "react-router-dom";
import Main from "./pages/main";
import NewInterview from "./pages/newInterview";
import Report from "./pages/report";
import {useEffect, useState} from "react";
import axios from "axios";
import "./index.css"


function App() {
    const [state, setState] = useState([])
    const [activeUser, setActiveUser] = useState();

  return (
      <BrowserRouter>
        <Routes>

          <Route
              element={<Main data={state} setActiveUser={setActiveUser} activeUser={activeUser} />}
              path={'/'}
              exact />
          <Route
              element={<NewInterview />}
              path={'/new/interview'}
              exact />
          <Route
              element={<Report/> }
              path={'/report'}
              exact />
        </Routes>
   </BrowserRouter>
  );
}

export default App;
