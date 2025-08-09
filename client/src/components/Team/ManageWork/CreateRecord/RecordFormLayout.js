import { useState } from 'react';
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

const FieldValue = ({ field, onChange }) => {
    const { fieldId, fieldType, data, editable } = field || {};
    const isReadOnly = !editable;

    switch (fieldType) {
        case 'STRING': {
            return (
                <input
                    type="text"
                    className="field-value"
                    value={data || ""}
                    name={`fieldValue_${fieldId}`}
                    placeholder="Enter text"
                    readOnly={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                />
            );
        }

        case 'USER': {
            return <p className="field-value">User select box will come here...</p>;
        }

        case 'DATE_TIME': {
            const date = new Date(data);
            const pad = num => num.toString().padStart(2, '0');
            const formatted = data
                ? `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}`
                : "";
            return (
                <input
                    type="datetime-local"
                    className="field-value"
                    value={formatted}
                    readOnly={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                />
            );
        }

        case 'TEXT': {
            return (
                <textarea
                    className="field-value"
                    placeholder={field.fieldName}
                    value={data || ""}
                    readOnly={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                />
            );
        }

        case 'SELECT': {
            const selectedValue = data?.find(opt => opt.selected)?.value || "";
            return (
                <select
                    className="field-value"
                    value={selectedValue}
                    disabled={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                >
                    <option value="" disabled>
                        Select an option
                    </option>
                    {data?.map(({ value }, index) => (
                        <option key={index} value={value}>
                            {value}
                        </option>
                    ))}
                </select>
            );
        }

        case 'TRANSITION': {
            const { name, score } = data || {};
            return name && (
                <p className="field-value transition-value" title={`Score: ${score}`}>
                    {name}
                </p>
            );
        }

        case 'CURRENCY': {
            const selectedCurrency = data?.currency?.find(c => c.selected)?.code || "";
            return (
                <div className="field-value currency-field FRCS">
                    <select
                        className="field-value"
                        value={selectedCurrency}
                        disabled={isReadOnly}
                        onChange={e => editable && onChange(fieldId, e.target.value)}
                    >
                        <option value="" disabled>
                            Select a Currency
                        </option>
                        {data?.currency?.map(({ code, name }, index) => (
                            <option key={index} value={code} title={name}>
                                {code}
                            </option>
                        ))}
                    </select>
                    <input
                        type="number"
                        className="field-value"
                        value={data?.amount || ""}
                        placeholder="Enter Amount"
                        readOnly={isReadOnly}
                        onChange={e => editable && onChange(fieldId, e.target.value)}
                    />
                </div>
            );
        }

        case 'GEO_LOCATION': {
            return <p className="field-value">Geo location select box will come here...</p>;
        }

        case 'FILE': {
            return (
                <input
                    type="file"
                    className="field-value"
                    disabled={isReadOnly}
                />
            );
        }

        case 'LINK': {
            return (
                <input
                    type="url"
                    className="field-value"
                    placeholder="Enter URL"
                    value={data || ""}
                    readOnly={isReadOnly}
                    onChange={editable ? () => { } : undefined}
                />
            );
        }
    }
};

const RecordField = ({ field, componentType, onChange }) => {

    const { showInDetail, fieldType, required, editable, classification } = field || {};
    if (classification == 'SYSTEM_FIELD' && componentType == 'create') {
        return null; // Skip system fields only on creating record
    }
    return showInDetail && (
        <div className={`FRSS w100 record-field ${showInDetail ? 'show-in-detail' : ''} ${fieldType.toLowerCase()} ${editable ? 'editable' : ''}`}>
            <FieldName fieldType={fieldType} fieldName={field.fieldName} required={required} />
            <FieldValue field={field} onChange={onChange} />
        </div>
    )
}

const RecordFormLayout = ({ fields, componentType }) => {

    const [formData, setFormData] = useState(() =>
        Object.fromEntries(fields.map(f => [f.fieldId, f.data || ""]))
    );

    const handleChange = (fieldId, value) => {
        setFormData(prev => ({ ...prev, [fieldId]: value }));
    };

    return (
        <div id='record-form-layout' className='FCCS'>
            <div id='slider' className='FCCS'>
                {fields?.map((field) => (
                    <RecordField
                        key={field.fieldId}
                        field={{ ...field, data: formData[field.fieldId] }}
                        componentType={componentType}
                        onChange={handleChange}
                    />
                ))}
            </div>
        </div>
    );
};


export default RecordFormLayout