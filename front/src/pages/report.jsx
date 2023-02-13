import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import ComboBox from "react-responsive-combo-box";
import styles from "../css/report.module.css";
import Select from 'react-select'


function Report({data}) {
    const [userState, SetUserState] = useState([]);
    const [state, SetState] = useState([]);
    const MyComponent = () => (
        <Select options={users} />
    )

    const users = userState.map((item) => item.name)

    // const [activeUser, setActiveUser] = useState();


    function getReport(activeUser) {
        {
            if (activeUser)
                axios.get('http://localhost:8083/api/result/' + activeUser)
                    .then((res => SetState(res.data.question)))
        }
    }


    useEffect(() => {
        axios.get('http://localhost:8083/api/user',)
            .then((res => SetUserState(res.data)))
    }, [])

    return (
        <>
            <h1>Reports</h1>
            <Link to={'/'}>
                <button> Main</button>
            </Link>
            <Link to={'/new/interview'}>
                <button> New interview</button>
            </Link>
            <Link to={'/report'}>
                <button> Report</button>
            </Link>

            <div>
                <ComboBox
                    options={userState.map((item) => item.name)}
                    placeholder="choose user"
                    defaultIndex={0}
                    optionsListMaxHeight={300}
                    onSelect={(user) => getReport(user)
                    }
                    focusColor="#20C374"
                    renderOptions={(option) => (
                        <div>{option}</div>

                    )}
                />


                <MyComponent>
                    My Component
                </MyComponent>


                {/*<button onClick={getReport}>*/}
                {/*    getReport*/}
                {/*</button>*/}
            </div>


            <div>
                <table>
                    <thead>
                    <tr>
                        <th>theme</th>
                        <th>description</th>
                        <th>mark</th>
                        <th>comment</th>
                    </tr>
                    </thead>
                    {/*<tr>*/}
                    {/*    <th>Company</th>*/}
                    {/*    <th>Contact</th>*/}
                    {/*    <th>Country</th>*/}
                    {/*</tr>*/}
                    {
                        state.map(({description, theme, mark, comment}) => (
                            <tr key={description}>
                                <td>{theme}</td>
                                <td>{description}</td>
                                <td>{mark}</td>
                                <td>{comment}</td>
                            </tr>
                        ))
                    }
                </table>
            </div>

        </>
    );
}

export default Report;