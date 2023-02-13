import {Link} from "react-router-dom";
import ComboBox from "react-responsive-combo-box";
import styles from '../css/main.module.css'

function Main({data, setActiveUser, activeUser}) {

    // const reportButton = activeUser
    //     ? <Link to={'/3'}>
    //         <button>get Report</button>
    //     </Link>
    //     : <button>get Report</button>

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

            {/*<ComboBox*/}
            {/*    options={data.map((item) => item.name)}*/}
            {/*    placeholder="choose user"*/}
            {/*    defaultIndex={4}*/}
            {/*    optionsListMaxHeight={300}*/}
            {/*    onSelect={(user) => setActiveUser(user)}*/}
            {/*    style={{*/}
            {/*        width: "350px",*/}
            {/*        margin: "0 auto",*/}
            {/*        marginTop: "50px"*/}
            {/*    }}*/}
            {/*    focusColor="#20C374"*/}
            {/*    renderOptions={(option) => (*/}
            {/*        <div>{option}</div>*/}
            {/*    )}*/}
            {/*/>*/}
            {/*{reportButton}*/}
        </>
    );
}

export default Main;
