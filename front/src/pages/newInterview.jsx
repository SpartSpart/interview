import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import styles from "../css/newinterview.module.css";
import {v4 as uuidv4} from 'uuid';

const updatedState = (arr) => arr.reduce((acc, item) => {
    if (acc[item.theme]) {
        acc[item.theme].push(item);
        return acc;
    }

    acc[item.theme] = [];
    acc[item.theme].push(item);
    return acc;
}, {});

const questionInputs = ['mark', 'comment'];

// const newQuestionInput = ['description', 'answer', 'mark', 'comment'];

function NewInterview() {
    const [tabs, setTabs] = useState([]);
    const [activeTab, setActiveTab] = useState()
    const [question, setQuestion] = useState([]);
    const [data, setData] = useState({user: {}, question: []});
    const [showAdd, setShowAdd] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8083/api/question/getall')
            .then((res) => updatedState(res.data))
            .then((res) => {
                setTabs(Object.keys(res));
                setQuestion(res);
            })
    }, [])

    const handleUserChangeClick = (e) => {
        const {name, value} = e.target;

        setData((prev) => ({...prev, user: {...prev.user, [name]: value}}))
    }

    const handleQuestionChangeClick = (e, description) => {
        const {name, value} = e.target;

        const question = data.question;
        const index = question.findIndex((item) => item.description === description);
        const emptyField = name === 'mark' ? {comment: ''} : {mark: ''};

        if (index === -1) {
            const newObj = {
                description,
                [name]: value,
                theme: activeTab,
                ...emptyField,
            }

            setData((prev) => ({...prev, question: [...prev.question, newObj]}))
        } else {
            const el = question[index];
            const updatedEl = {...el, [name]: value};
            setData((prev) => (
                {
                    ...prev,
                    question: [...prev.question.slice(0, index), updatedEl, ...prev.question.slice(index + 1)]
                }
            ))
        }
    }

    const handleSubmitClick = (e) => {
        e.preventDefault();

        if (!data.question.length) return;
        data.user.id = uuidv4();
        axios.post('http://localhost:8083/api/result', {...data})


    }

    const handleAddQuestion = (e) => {
        const {name, value} = e.target;
        let state;
        if (name === 'description') {
            state = data.question.find((item) => item.description === name);

            if (state) {
                return;
            } else {
                setData((prev) => ({
                    ...prev,
                    question: []
                }))
            }
        }
    }


    return (
        <>
            <h1>New Interview</h1>
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
                <form action="">
                    {

                        <label key='userInfo'>

                            <input
                                placeholder={'Name'}
                                type="text"
                                id="name"
                                name="name"
                                onChange={handleUserChangeClick}/>
                            <br/>

                            <input
                                placeholder={'Age'}
                                type="number"
                                id="age"
                                name="age"
                                onChange={handleUserChangeClick}/>
                            <br/>

                            <input
                                placeholder={'date'}
                                type="date"
                                id="date"
                                name="date"
                                onChange={handleUserChangeClick}/>
                            <br/>

                        </label>
                    }
                </form>
            </div>

            <div>
                {
                    tabs
                    && tabs.map((item) => {
                        return <button
                            className={styles.button}
                            onClick={() => setActiveTab(item)}
                            key={item}>
                            {item}
                        </button>
                    })
                }
                {
                    activeTab
                    && question[activeTab]
                    && question[activeTab].map(({description, answer}, i) => {
                        return (
                            <div key={i + description} className={styles.questionComponent}>
                                <p className={styles.questionText}>
                                    <span className={styles.bold}> Question: </span>
                                    {description}</p>
                                <p className={styles.answerText}>
                                    <span className={styles.bold}> Answer: </span>
                                    {answer}</p>
                                <form action="">
                                    {
                                        questionInputs.map((item) => {
                                            const jaLox = data.question.find((item) => item.description === description);

                                            return <label key={item}>
                                                <input
                                                    value={(jaLox && jaLox[item]) || ''}
                                                    name={item}
                                                    placeholder={item}
                                                    className={item === 'mark' ? styles.markInput : styles.commentInput}
                                                    type={item === 'mark' ? "number" : "text"}
                                                    onChange={(e) => handleQuestionChangeClick(e, description)}
                                                />
                                            </label>
                                        })
                                    }
                                </form>
                            </div>
                        )
                    })
                }
            </div>
            {
                // showAdd && newQuestionInput.map((u) => (
                //     <label key={u}>
                //         <span>{u}</span>
                //         <input name={u} onChange={handleAddQuestion} type="text"/>
                //     </label>
                // ))
            }

            {/*<button*/}
            {/*    // visibility = 'hidden'//{(!data.question.length ? 'hidden' : 'visible')}*/}
            {/*    className={styles.button}*/}
            {/*    onClick={() => setShowAdd((prev) => !prev)}>*/}
            {/*    Add Question*/}
            {/*</button>*/}
            <button
                // className={styles.button}
                type="submit"
                onClick={handleSubmitClick}>
                Submit
            </button>
        </>
    );
}

export default NewInterview;