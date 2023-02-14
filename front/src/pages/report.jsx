import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import ComboBox from "react-responsive-combo-box";
import styles from "../css/report.module.css";
import Select from 'react-select'


function Report({data}) {
    const [userState, SetUserState] = useState([]);
    const [state, SetState] = useState([]);
    const totalQuestions = state.length;
    const totalMark = state.reduce((acc, element) => +element.mark + acc, 0)
    const averageValue = (totalMark / totalQuestions).toFixed(3)

    const users = userState.map((item) => ({value: item.name, label: item.name + ' | ' + item.date}))

    function getReport(activeUser) {
        {
            if (activeUser) {
                axios.get('http://localhost:8083/api/result/' + activeUser)
                    .then((res => {
                        SetState(res.data.question)
                        //   SetTotalMark(res.data.question.map(u=>+u.mark).reduce((totalQuestions, mark) => totalQuestions + mark));
                    }))

            }
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
                <Select
                    className={styles.select}
                    options={users.sort()}
                    onChange={(user) => getReport(user.value)}>
                </Select>
            </div>
            <h2> Result </h2>

            <div>
                <table id={'ResultTable'}>
                    <thead>
                    <tr>
                        <th>theme</th>
                        <th>description</th>
                        <th>mark</th>
                        <th>comment</th>
                    </tr>
                    </thead>
                    {

                        state.map(({description, theme, mark, comment}) => (
                            <tr key={description}>
                                <td className={styles.tr}>{theme}</td>
                                <td className={styles.tr}> {description}</td>
                                <td className={styles.tr}>{mark}</td>
                                <td className={styles.tr}>{comment}</td>
                            </tr>
                        ))
                    }

               </table>
            </div>

            <div>
                <h2> Total </h2>
                <table>
                    <thead>
                    <th>Questions count</th>
                    <th>Total Mark</th>
                    <th>Average score</th>
                    </thead>
                    <tr>
                        <td className={styles.tr}>
                            {totalQuestions}
                        </td>
                        <td className={styles.tr}>
                            {totalMark}
                        </td>
                        <td className={styles.tr}>
                            {averageValue}
                        </td>
                    </tr>

                </table>

            </div>
        </>
    );
}


export default Report;