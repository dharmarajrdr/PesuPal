import './DepartmentLayout.css';
import UserAvatar from "../User/UserAvatar";
import { useEffect } from "react";
import { apiRequest } from "../../http_request";
import { useDispatch, useSelector } from "react-redux";
import { setCurrentDepartmentHead, setCurrentDepartmentId, setCurrentDepartmentName, setDepartments } from "../../store/reducers/DepartmentSlice";

// Renders the department dropdown
const DepartmentList = () => {

    const dispatch = useDispatch();
    const { departments, currentDepartment } = useSelector(state => state.department) || {};
    const { 'id': departmentId } = currentDepartment || {};

    useEffect(() => {

        apiRequest('/api/v1/department/all', 'GET').then(({ data }) => {

            dispatch(setDepartments(data));

            // Set current department only if not already set
            const current = data.find(d => d.id === departmentId);
            if (current) {
                dispatch(setCurrentDepartmentId(current.id));
                dispatch(setCurrentDepartmentName(current.name));
                dispatch(setCurrentDepartmentHead(current.head));
            }
        }).catch(({ message }) => {
            console.error("Error fetching departments:", message);
        });
    }, [currentDepartment?.id, dispatch]);

    const chooseDepartmentHandler = (e) => {
        const selectedDepartment = departments.find(dept => dept.id === e.target.value);
        if (selectedDepartment) {
            dispatch(setCurrentDepartmentId(selectedDepartment.id));
            dispatch(setCurrentDepartmentName(selectedDepartment.name));
        }
    };

    return (
        <div className="FCSS">
            <h5 id="chooseDepartment">Choose Department</h5>
            <select id="departmentList" onChange={chooseDepartmentHandler} value={departmentId}>
                {departments?.map(({ id, name }) => (
                    <option key={id} value={id}>{name}</option>
                ))}
            </select>
        </div>
    );
};

// Renders department head with crown
const DepartmentHead = () => {

    const dispatch = useDispatch();
    const { currentDepartment } = useSelector(state => state.department) || {};
    const { head } = currentDepartment || {};

    useEffect(() => {

        if (!currentDepartment?.id) return;

        apiRequest(`/api/v1/department/${currentDepartment.id}`, 'GET').then(({ data }) => {
            dispatch(setCurrentDepartmentId(data.id));
            dispatch(setCurrentDepartmentName(data.name));
            dispatch(setCurrentDepartmentHead(data.head));
        }).catch(({ message }) => {
            console.error("Error fetching department data:", message);
        });

    }, [currentDepartment?.id, dispatch]);

    return head ? (
        <div className="FRCB" id="departmentHead">
            <UserAvatar displayPicture={head.displayPicture} />
            <div className="FCSS">
                <h5 id="headName">{head.displayName}</h5>
                <p id="headDesignation">{head.designation}</p>
            </div>
            <i className="fa-solid fa-crown shine-crown" title="Department Head"></i>
        </div>
    ) : null;
};

// Main component
const DepartmentHeader = () => {

    return (
        <div id="departmentHeader" className="w100 FRCB p10">
            <DepartmentList />
            <DepartmentHead />
        </div>
    );
};

export default DepartmentHeader;
