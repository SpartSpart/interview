import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import ComboBox from "react-responsive-combo-box";
import styles from "../css/report.module.css";
import Select from 'react-select'
import alertify from "alertifyjs";

function Report() {
    const [userState, SetUserState] = useState([]);
    const [questionsState, SetQuestionsState] = useState([]);
    const [themes, SetThemes] = useState([]);

    const totalQuestions = questionsState.length;
    const totalMark = questionsState.reduce((acc, element) => +element.mark + acc, 0)
    const averageValue = (totalMark / totalQuestions).toFixed(3)

    const users = userState.map((item) => ({value: item.name, label: item.name + ' | ' + item.date}))

    function getReport(activeUser) {
        {
            if (activeUser) {
                axios.get('http://localhost:8083/api/result/' + activeUser)
                    .then((res => {
                        SetQuestionsState(res.data.question)
                       // SetThemes(Object.keys(res.data.question.theme))
                    }))
                    // .then(()=>alert("Success"))
                    .catch((error) => {
                        console.log(error)
                        alertify.alert(error.message)
                    });
            }
        }
    }

    useEffect(() => {
        axios.get('http://localhost:8083/api/user',)
            .then((res => SetUserState(res.data)))
            // .catch((error) => alertify.alert(error.message));
    }, [])

    const totalThemes = questionsState && Array.from(new Set(questionsState.map((item) => item.theme)))

    const detailReportInfo = totalThemes.map(theme => {
        const allQuestionsByTheme = questionsState.filter((item) => item.theme === theme);
        const numberOfQuestions = allQuestionsByTheme.length;
        const totalMark = allQuestionsByTheme.reduce((acc, item) => (+item.mark + acc), 0)
        const averageScore = (totalMark / numberOfQuestions).toFixed(3);
        const grade = getGrade(averageScore)

        return {
            theme,
            numberOfQuestions,
            totalMark,
            averageScore,
            grade
        }
    })

    function getGrade(value) {
        if (value < 1) return 'none';
        if (value < 2) return 'common';
        if (value < 3) return 'basic';
        if (value < 4) return 'good';
        if (value >= 4) return 'necessary';
    }

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
                        questionsState.map(({description, theme, mark, comment}) => (
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
                <h2> Detail </h2>
                <table>
                    <thead>
                    <th>Theme</th>
                    <th>Number of questions</th>
                    <th>Total Mark</th>
                    <th>Average score</th>
                    <th>Grade</th>
                    </thead>
                    {
                        detailReportInfo.map(({theme, numberOfQuestions, totalMark, averageScore, grade}) => (
                            <tr key={theme}>
                                <td className={styles.tr}>{theme}</td>
                                <td className={styles.tr}> {numberOfQuestions}</td>
                                <td className={styles.tr}>{totalMark}</td>
                                <td className={styles.tr}>{averageScore}</td>
                                <td className={styles.tr}>{grade}</td>
                            </tr>
                        ))
                    }
                </table>
            </div>

            <div>
                <h2> Total </h2>
                <table>
                    <thead>
                    <th>Number of questions</th>
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