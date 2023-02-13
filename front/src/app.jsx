import {BrowserRouter, Routes, Route} from "react-router-dom";
import Main from "./pages/main";
import NewInterview from "./pages/newInterview";
import Report from "./pages/report";
import {useEffect, useState} from "react";
import axios from "axios";


function App() {
    const [state, setState] = useState([])
    const [activeUser, setActiveUser] = useState();

    // useEffect(() => {
    //     axios.get('http://localhost:8083/api/user',)
    //         .then((res => setState(res.data)))
    // }, [])
    //
    // if(!state.length) {
    //     return <h1>Loading</h1>
    // }

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




   {/*<h1>Привет</h1>*/}
      </BrowserRouter>
  );
}

export default App;
