import {Link} from "react-router-dom";
import styles from '../css/main.module.css'
import {useEffect, useState} from "react";
import axios from "axios";
import alertify from "alertifyjs";

function Main() {
    const [state, setState] = useState()

    const handleFileSubmit = () => {
        if (state == undefined) {
            alertify
                .alert('Choose file')
        }

        console.log(state)

        const formData = new FormData();
        formData.append('file', state);

        axios.post(
            'http://localhost:8083/api/question/upload',
            formData,
            {
                headers: {
                    "Content-type": "multipart/form-data"
                },
            }
        )
            .then(() => alertify.alert('Success'))
            .catch((error) => alertify.alert(error.message));
    }

    return (
        <>
            <h1 className={styles.h1}>Main</h1>
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
                <label className='labelFile'>
                    <span className='inputFile'>Выберите файл</span>
                <input type="file" onChange={(e) => setState(e.target.files[0])}/>
                </label>
                {state && state.name}
                <button onClick={handleFileSubmit}>Отправить</button>
            </div>
        </>
    );
}

export default Main;
