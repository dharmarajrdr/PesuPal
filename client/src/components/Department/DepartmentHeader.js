import { useEffect, useState } from "react";
import './DepartmentLayout.css';
import { apiRequest } from "../../http_request";

// Renders the department dropdown
const DepartmentList = ({ setCurrentDepartment, departmentId }) => {
    const [allDepartments, setAllDepartments] = useState([]);

    useEffect(() => {
        apiRequest('/api/v1/department/all', 'GET').then(({ data }) => {
            setAllDepartments(data);

            // Set current department only if not already set
            const current = data.find(d => d.id === departmentId);
            if (current) {
                setCurrentDepartment(current);
            }
        }).catch(({ message }) => {
            console.error("Error fetching departments:", message);
        });
    }, [departmentId, setCurrentDepartment]);

    const chooseDepartmentHandler = (e) => {
        const selectedDepartment = allDepartments.find(dept => dept.id === parseInt(e.target.value));
        if (selectedDepartment) {
            setCurrentDepartment(selectedDepartment);
        }
    };

    return (
        <div className="FCSS">
            <h5 id="chooseDepartment">Choose Department</h5>
            <select id="departmentList" onChange={chooseDepartmentHandler} value={departmentId}>
                {allDepartments.map(({ id, name }) => (
                    <option key={id} value={id}>{name}</option>
                ))}
            </select>
        </div>
    );
};

// Renders department head with crown
const DepartmentHead = ({ head }) => {
    return head ? (
        <div className="FRCB" id="departmentHead">
            <img src={head.displayPicture} alt="Head" className="img_40_40 mR10 objectFitCover" />
            <div className="FCSS">
                <h5 id="headName">{head.displayName}</h5>
                <p id="headDesignation">{head.designation}</p>
            </div>
            <i className="fa-solid fa-crown shine-crown" title="Department Head"></i>
        </div>
    ) : null;
};

// Main component
const DepartmentHeader = ({ departmentId }) => {

    const [currentDepartment, setCurrentDepartment] = useState(null);
    const [currentDepartmentData, setCurrentDepartmentData] = useState({});

    useEffect(() => {
        if (!currentDepartment?.id) return;

        apiRequest(`/api/v1/department/${currentDepartment.id}`, 'GET').then(({ data }) => {
            setCurrentDepartmentData(data);
        }).catch(({ message }) => {
            console.error("Error fetching department data:", message);
        });

    }, [currentDepartment?.id]);

    return (
        <div id="departmentHeader" className="w100 FRCB p10">
            <DepartmentList setCurrentDepartment={setCurrentDepartment} departmentId={currentDepartment?.id || departmentId} />
            <DepartmentHead head={currentDepartmentData?.head} />
        </div>
    );
};

export default DepartmentHeader;
