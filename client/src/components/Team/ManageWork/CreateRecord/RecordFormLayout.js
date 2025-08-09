import data from './data';
import './RecordFormLayout.css';

const fieldIcons = {
    "STRING": "fa fa-edit",
    "USER": "fa fa-user",
    "DATE_TIME": "fa fa-calendar",
    "TEXT": "fa fa-paragraph",
    "SELECT": "fa fa-list",
    "TRANSITION": "fa fa-chart-line",
    "CURRENCY": "fa fa-dollar-sign",
    "GEO_LOCATION": "fa fa-map-marker-alt",
    "FILE": "fa fa-file",
    "LINK": "fa fa-link"
}

const FieldName = ({ fieldName, fieldType, required }) => {

    return <div className='field-name'>
        <i className={`mR10 w15 color777 ${fieldIcons[fieldType] || 'fa fa-question'}`} aria-hidden='true'></i>
        <span className='field-name-text'>{fieldName}</span>
        {required && <span className='required'>*</span>}
    </div>;
}

const FieldValue = ({ field }) => {

    const { fieldType, data, editable } = field || {};

    switch (fieldType) {

        case 'STRING': {
            return <input type='text' className='field-value' placeholder={field.fieldName} value={data} readOnly={!editable} />;
        }
        case 'USER': {
            return <p className='field-value'>User select box will come here...</p>
        }
        case 'DATE_TIME': {
            const date = new Date(data);
            const pad = num => num.toString().padStart(2, '0');
            const formatted = `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
            return <input type='datetime-local' className='field-value' value={formatted} readOnly={!editable} />;
        }
        case 'TEXT': {
            return <textarea className='field-value' placeholder={field.fieldName} value={data} readOnly={!editable} />;
        }
        case 'SELECT': {
            return (
                <select className='field-value' disabled={!editable}>
                    {data.map(({ value, selected }, index) => (
                        <option key={index} value={value} selected={selected}>
                            {value}
                        </option>
                    ))}
                </select>
            );
        }
        case 'TRANSITION': {
            const { name, score } = data || {};
            return <p className='field-value transition-value' title={`Score: ${score}`}>{name}</p>
        }
        case 'CURRENCY': {
            const { currency, amount } = data || {};
            return <div className='field-value currency-field FRCS'>
                <select className='field-value'>
                    {currency.map(({ code, selected, name }, index) => (
                        <option key={index} value={code} selected={selected} title={name}>{code}</option>
                    ))}
                </select>
                <input type='number' className='field-value' value={amount} readOnly={!editable} />
            </div>
        }
        case 'GEO_LOCATION': {
            return <p className='field-value'>Geo location select box will come here...</p>
        }
        case 'FILE': {
            return <input type='file' className='field-value' disabled={!editable} />;
        }
        case 'LINK': {
            return <input type='url' className='field-value' placeholder='Enter URL' disabled={!editable} />;
        }
    }
}

const RecordField = ({ field }) => {

    const { showInDetail, fieldType, required, editable } = field || {};
    return (
        <div className={`FRSS w100 record-field ${showInDetail ? 'show-in-detail' : ''} ${fieldType.toLowerCase()} ${editable ? 'editable' : ''}`}>
            <FieldName fieldType={fieldType} fieldName={field.fieldName} required={required} />
            <FieldValue field={field} />
        </div>
    )
}

const RecordFormLayout = () => {

    return (
        <div id='record-form-layout' className='FCCS'>
            <div id='slider' className='FCCS'>
                {data.map((field) => (
                    <RecordField key={field.fieldId} field={field} />
                ))}
            </div>
        </div>
    )
}

export default RecordFormLayout